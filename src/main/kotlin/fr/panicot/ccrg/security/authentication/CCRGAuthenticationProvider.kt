package fr.panicot.ccrg.security.authentication

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*

/**
 * Created by William on 12/02/2017.
 */

open class CCRGAuthenticationProvider: AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication? {
        return createSuccessAuthentication(authentication)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

    fun createSuccessAuthentication(authentication: Authentication): Authentication {
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        val result = UsernamePasswordAuthenticationToken(authentication.principal, authentication.credentials, Arrays.asList(SimpleGrantedAuthority("ROLE_USER")))
        result.details = authentication.details

        return result
    }
}