package eu.javimar.domain.auth.usecases

import eu.javimar.domain.auth.utils.IFormValidator
import eu.javimar.domain.auth.utils.ValidationPassResult
import eu.javimar.domain.base.UseCase
import javax.inject.Inject

class ValidatePassUseCase @Inject constructor(
    private val passwordChecker: IFormValidator,
): UseCase<String, ValidationPassResult>() {
    
    override suspend fun useCaseFunction(input: String): ValidationPassResult {
        val passwordCheck = passwordChecker.passwordIsValid(input)
        val containMayusMinusNumber =
            passwordCheck.containMayus &&
            passwordCheck.containMinus &&
            passwordCheck.containNumber

        return ValidationPassResult(
            passwordCheck.correctLength,
            passwordCheck.containSymbol,
            containMayusMinusNumber
        )
    }
}