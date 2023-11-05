package eu.javimar.domain.auth.usecases

import eu.javimar.domain.auth.enums.AuthFormErrors
import eu.javimar.domain.auth.utils.IFormValidator
import eu.javimar.domain.auth.utils.ValidationResult
import eu.javimar.domain.base.UseCase
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val emailValidator: IFormValidator
): UseCase<String, ValidationResult>() {

    override suspend fun useCaseFunction(input: String): ValidationResult {
        if(!emailValidator.isValidEmail(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = AuthFormErrors.INCORRECT_EMAIL
            )
        }
        return ValidationResult(successful = true)
    }
}