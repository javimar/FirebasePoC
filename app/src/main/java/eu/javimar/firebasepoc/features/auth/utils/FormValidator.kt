package eu.javimar.firebasepoc.features.auth.utils

import android.util.Patterns
import eu.javimar.domain.auth.utils.IFormValidator
import eu.javimar.domain.auth.utils.PasswordCheckerResult
import java.util.regex.Pattern

class FormValidator: IFormValidator {

    override fun isValidEmail(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(email).matches()
    }

    override fun isValidWebsite(website: String): Boolean {
        return Patterns.WEB_URL.matcher(website).matches()
    }

    override fun passwordIsValid(password: String): PasswordCheckerResult {
        val passwordCheckerResult = PasswordCheckerResult()
        passwordCheckerResult.containMinus = containMinus(password)
        passwordCheckerResult.containMayus = containMayus(password)
        passwordCheckerResult.containSymbol = containSymbol(password)
        passwordCheckerResult.containNumber = containNumber(password)
        passwordCheckerResult.correctLength = hasCorrectLength(password)

        return passwordCheckerResult
    }

    private fun containMinus(password: String): Boolean {
        val lowerCaseRegExp = Pattern.compile("^(?=.*[a-z])")
        return lowerCaseRegExp.matcher(password).find()
    }

    private fun containMayus(password: String): Boolean {
        val upperCaseRegExp = Pattern.compile("^(?=.*[A-Z])")
        return upperCaseRegExp.matcher(password).find()
    }

    private fun containSymbol(password: String): Boolean {
        val symbolRegExp = Pattern.compile("^(?=.*[*@#$%^\\-&+|=<>\"€.·\\[{}\\]`_,´¨ç;!¡?¿:()])")
        return symbolRegExp.matcher(password).find()
    }

    private fun containNumber(password: String): Boolean {
        val numberRegExp = Pattern.compile("^(?=.*[0-9])")
        return numberRegExp.matcher(password).find()
    }

    private fun hasCorrectLength(password: String): Boolean {
        return password.length > 9
    }
}