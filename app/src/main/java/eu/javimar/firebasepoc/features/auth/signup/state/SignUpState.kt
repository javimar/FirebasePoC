package eu.javimar.firebasepoc.features.auth.signup.state

import eu.javimar.domain.auth.enums.AuthFormErrors

data class SignUpState(
    val email: String = "",
    val emailError: AuthFormErrors? = null,
    val password: String = "",
    val confirmPassword: String = "",
    val confirmPasswordError: AuthFormErrors? = null,
    val checkPassLength: Boolean = false,
    val checkPassSymbol: Boolean = false,
    val checkPassMayusMinusNumber: Boolean = false,
)
