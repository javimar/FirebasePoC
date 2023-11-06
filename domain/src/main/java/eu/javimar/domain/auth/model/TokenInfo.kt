package eu.javimar.domain.auth.model

import eu.javimar.domain.auth.enums.Providers

data class TokenInfo(
    val userId: String = "",
    val issuedAt: Long = 0L,
    val authTime: Long = 0L,
    val expiration: Long = 0L,
    val identities: String = "",
    val signInProvider: Providers = Providers.UNKNOWN,
)

fun getSignInProvider(provider: String?): Providers =
    when {
        provider == "anonymous" -> Providers.ANONYMOUS
        provider?.contains("google", ignoreCase = true) == true -> Providers.GOOGLE
        provider?.contains("password", ignoreCase = true) == true -> Providers.PASSWORD
        else -> Providers.UNKNOWN
    }
