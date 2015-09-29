
package org.sj.ldap;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import javax.naming.NamingException;


public class LdapAuthTest {
    private String user = "";
    private char[] pass = "password".toCharArray();

    @Test
    public void testAutenticate() throws NamingException, IOException {
        LdapAuth auth = new LdapAuth();
        auth.setSsl(false);
        auth.setLdapURL(
                "ldap://ldap.forumsys.com");
        auth.setBaseDN("cn=read-only-admin,dc=example,dc=com");
        auth.setAuthType(LdapAuth.AUTH_TYPE.SIMPLE);
        boolean res = auth.autenticate(user, pass);
        Assert.assertTrue(res);
    }

    @Test
    public void testMemberOf() throws NamingException, IOException {
        LdapAuth auth = new LdapAuth();
        auth.setSsl(false);
        auth.setLdapURL("ldap://ldap.forumsys.com");
        auth.setBaseDN("cn=read-only-admin,dc=example,dc=com");
        auth.setAuthType(LdapAuth.AUTH_TYPE.SIMPLE);
        auth.setFollowReferrals(true);
        boolean res = auth.autenticate(user, pass, "mathematicians");
        Assert.assertTrue(res);
    }
}
