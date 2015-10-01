/**
 * Copyright Sangram Jadhav. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package org.sj.ldap;

import java.io.IOException;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public class LdapAuth {

    /** Active Directory Authentication types. May need some extra setup for Kerberos/GSSAPI */
    public enum AUTH_TYPE {
        NONE("none"), SIMPLE("simple"), DIGEST_MD5("DIGEST-MD5"), GSSAPI("GSSAPI") ;
        
        private final String value;

        private AUTH_TYPE(String value) {
            this.value = value;
        }
    
        public String getValue() {
            return this.value;
        }
    };

    private static final String REFERREL_FOLLOW = "follow";
    private static final String SEARCH_BY_SAM_ACCOUNT_NAME = "(sAMAccountName=%s)";
    private static final String SEARCH_GROUP_BY_GROUP_CN = "(&(objectCategory=group)(cn={0}))";
    private static final String MEMBER_OF = "memberOf";
    private static final String[] attributesForSearch = new String[]{MEMBER_OF};
        
    private boolean ssl;
    private String ldapURL;
    private String baseDN;
    private AUTH_TYPE authType;
    private boolean followReferrals;
    
    public LdapAuth(){
        this.authType = AUTH_TYPE.NONE;
    }
    
    public LdapAuth(String ldapURL, String baseDN){
        this.ldapURL = ldapURL;
        this.baseDN = baseDN;
        this.ssl = false;
        this.authType = AUTH_TYPE.NONE;
    }
    
    public LdapAuth(String ldapURL, String baseDN, boolean ssl){
        this.ldapURL = ldapURL;
        this.baseDN = baseDN;
        this.ssl = ssl;
        this.authType = AUTH_TYPE.NONE;
    }
    
    public LdapAuth(String ldapURL, String baseDN, boolean ssl, AUTH_TYPE authType){
        this.ldapURL = ldapURL;
        this.baseDN = baseDN;
        this.ssl = ssl;
        this.authType = authType;
    }
    
    public LdapAuth(String ldapURL, String baseDN, boolean ssl, AUTH_TYPE authType, boolean followReferrals){
        this.ldapURL = ldapURL;
        this.baseDN = baseDN;
        this.ssl = ssl;
        this.authType = authType;
        this.followReferrals = false;
    }
    
    protected LdapContext connect(String login, char[] password) throws NamingException, IOException {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapURL);
        LdapContext ctx = null;
        StartTlsResponse tls = null;

        try {
            ctx = new InitialLdapContext(env, null);
            if (ssl) {
                tls = (StartTlsResponse) ctx.extendedOperation(new StartTlsRequest());
                tls.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        try {
                            Certificate[] cert = session.getPeerCertificates();
                        } catch (SSLPeerUnverifiedException e) {
                            return false;
                        }
                        return true;
                    }
                });
                tls.negotiate();
            }
            ctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, authType.getValue());
            if(followReferrals){
                ctx.addToEnvironment(Context.REFERRAL, REFERREL_FOLLOW);
            }
            if(null == login || login.isEmpty()){
                ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, this.baseDN);
            }else{
                ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, login);
            }
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            ctx.reconnect(null);
        } finally {
            if (tls != null) {
                try {
                    tls.close();
                } catch (IOException e) {

                }
                tls = null;
            }
        }

        return ctx;
    }

    public boolean autenticate(String login, char[] password) throws NamingException, IOException  {
        LdapContext ctx = null;
        try {
            ctx = connect(login, password);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                }
                ctx = null;
            }
        }
        return true;
    }

    public boolean autenticate(String login, char[] password, String groupName)  throws NamingException, IOException  {
        LdapContext ctx = null;
        try {
            ctx = connect(login, password);
            return memberOf(ctx, login, groupName);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                }
                ctx = null;
            }
        }
    }

    public boolean memberOf(LdapContext ctx, String login, String group) throws NamingException {
        Set<String> groups = memberOf(ctx, login);
        for (String tmp : groups) {
            if (parseDN(tmp).equals(group)) {
                return true;
            }
        }
        return false;

    }

    public Set<String> memberOf(LdapContext ctx, String login) throws NamingException {
        String filter = String.format(SEARCH_BY_SAM_ACCOUNT_NAME, login);
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        constraints.setReturningAttributes(attributesForSearch);
        NamingEnumeration results = ctx.search(baseDN, filter, constraints);
        if (results == null || !results.hasMore()) {
            return Collections.EMPTY_SET;
        }

        // Get result for the first entry found
        SearchResult result = (SearchResult) results.next();

        // Get the entry's distinguished name
        NameParser parser = ctx.getNameParser("");
        parser.parse(ctx.getNameInNamespace());
        parser.parse(baseDN);

        parser.parse(new CompositeName(result.getName()).get(0));

        // Get the entry's attributes
        Attributes attrs = result.getAttributes();
        Attribute attr = attrs.get(attributesForSearch[0]);

        NamingEnumeration e = attr.getAll();

        Set<String> groups = new HashSet<String>();

        while (e.hasMore()) {
            String value = (String) e.next();
            groups.add(value);
        }
        return groups;

    }

    public String parseDN(String source) {
        Pattern pattern = Pattern.compile("CN=([^,]+),");
        Matcher matcher = pattern.matcher(source);
        return matcher.find() ? matcher.group(1) : "";
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public void setLdapURL(String ldapURL) {
        this.ldapURL = ldapURL;
    }

    public void setBaseDN(String baseDN) {
        this.baseDN = baseDN;
    }
    
    public void setAuthType(AUTH_TYPE authType){
        this.authType = authType;
    }
    
    public void setFollowReferrals(boolean followReferrals){
        this.followReferrals = followReferrals;
    }
}
