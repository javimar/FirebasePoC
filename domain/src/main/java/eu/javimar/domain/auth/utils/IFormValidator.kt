package eu.javimar.domain.auth.utils

interface IFormValidator {
    fun isValidEmail(email: String): Boolean
    fun isValidWebsite(website: String): Boolean
    fun passwordIsValid(password: String): PasswordCheckerResult
}