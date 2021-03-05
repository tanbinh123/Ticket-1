package com.woniuxy.auth.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

@Slf4j
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms,
                                                            AuthenticationToken token) throws AuthenticationException {
        AuthenticationStrategy strategy = getAuthenticationStrategy();
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);
        if (log.isTraceEnabled()) {
            log.trace("Iterating through {} realms for PAM authentication", realms.size());
        }
        AuthenticationException authenticationException = null;
        for (Realm realm : realms) {
            aggregate = strategy.beforeAttempt(realm, token, aggregate);
            if (realm.supports(token)) {
                log.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm);
                AuthenticationInfo info = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (AuthenticationException e) {
                    authenticationException = e;
                    if (log.isDebugEnabled()) {
                        String msg = "Realm [" + realm + "] threw an exception during a multi-realm authentication attempt:";
                        log.debug(msg, e);
                    }
                }
                aggregate = strategy.afterAttempt(realm, token, info, aggregate, authenticationException);
            } else {
                log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
            }
        }
        //新增的代码
        if (authenticationException != null) {
            throw authenticationException;
        }
        aggregate = strategy.afterAllAttempts(token, aggregate);
        return aggregate;
    }
}
