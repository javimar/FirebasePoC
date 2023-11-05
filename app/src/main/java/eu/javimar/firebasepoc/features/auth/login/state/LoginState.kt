package eu.javimar.firebasepoc.features.auth.login.state

import eu.javimar.domain.auth.enums.AuthFormErrors

data class LoginState(
    val email: String = "",
    val emailError: AuthFormErrors? = null,
    val password: String = "",
    val passwordError: AuthFormErrors? = null,
)
