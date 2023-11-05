package eu.javimar.domain.auth.utils

import eu.javimar.domain.auth.enums.AuthFormErrors

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: AuthFormErrors? = null
)
