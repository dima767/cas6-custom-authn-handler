package org.example.dk.cas.authn;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apereo.cas.authentication.AcceptUsersAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.AsciiArtUtils;

import java.security.GeneralSecurityException;

import static java.lang.Runtime.getRuntime;

@Slf4j
public class CustomAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    private AuthenticationHandler delegate;

    public CustomAuthenticationHandler(String name,
                                       ServicesManager servicesManager,
                                       PrincipalFactory principalFactory,
                                       Integer order,
                                       AuthenticationHandler delegate) {

        super(name, servicesManager, principalFactory, order);
        this.delegate = delegate;
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential,
                                                                                        String originalPassword)
            throws GeneralSecurityException, PreventedException {

        val availableProcessors = getRuntime().availableProcessors();
        val totalMemory = getRuntime().totalMemory();
        val freeMemory = getRuntime().freeMemory();

        AsciiArtUtils.printAsciiArtInfo(LOGGER, "Memory stats",
                String.format("Available processors: %d%nTotal memory: %d%nFree memory: %d%n",
                        availableProcessors, totalMemory, freeMemory));

        return delegate.authenticate(credential);
    }
}
