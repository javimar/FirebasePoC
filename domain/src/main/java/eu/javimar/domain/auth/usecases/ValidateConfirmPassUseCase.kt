package eu.javimar.domain.auth.usecases

import eu.javimar.domain.auth.enums.AuthFormErrors
import eu.javimar.domain.auth.utils.ConfirmPassChecker
import eu.javimar.domain.auth.utils.ValidationResult
import eu.javimar.domain.base.UseCase
import javax.inject.Inject

class ValidateConfirmPassUseCase @Inject constructor():
    UseCase<ConfirmPassChecker, ValidationResult>() {
    
    override suspend fun useCaseFunction(input: ConfirmPassChecker): ValidationResult {
        if(input.password != input.confirmPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = AuthFormErrors.INCORRECT_CONFIRM_PASS
            )
        }
        return ValidationResult(successful = true)
    }
}