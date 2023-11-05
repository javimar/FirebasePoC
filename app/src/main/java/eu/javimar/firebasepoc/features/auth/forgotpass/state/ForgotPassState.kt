package eu.javimar.firebasepoc.features.auth.forgotpass.state

import eu.javimar.domain.auth.enums.AuthFormErrors

data class ForgotPassState(
    val email: String = "",
    val emailError: AuthFormErrors? = null,
)
