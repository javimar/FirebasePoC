package eu.javimar.firebasepoc.features.auth.utils

import eu.javimar.coachpoc.R
import eu.javimar.domain.auth.enums.AuthFormErrors

fun getErrorRes(error: AuthFormErrors): Int {
    return when(error) {
        AuthFormErrors.INCORRECT_EMAIL -> R.string.signup_form_email_format_error
        AuthFormErrors.INCORRECT_PASS -> R.string.signup_form_password_format_error
        AuthFormErrors.NO_CONFIRM_PASS -> R.string.signup_form_confirm_password_error
        AuthFormErrors.INCORRECT_CONFIRM_PASS -> R.string.signup_form_confirm_password_match_error
    }
}