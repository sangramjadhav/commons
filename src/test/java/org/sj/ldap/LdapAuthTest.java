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
