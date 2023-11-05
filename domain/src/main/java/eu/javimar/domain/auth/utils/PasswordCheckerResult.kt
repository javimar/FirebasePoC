package eu.javimar.domain.auth.utils

class PasswordCheckerResult {
    var containMinus = false
    var containMayus = false
    var containSymbol = false
    var containNumber = false
    var correctLength = false

    fun isValid(): Boolean {
        return containMinus &&
                containMayus &&
                containSymbol &&
                containNumber &&
                correctLength
    }
}