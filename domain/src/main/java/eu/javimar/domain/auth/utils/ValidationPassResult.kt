package eu.javimar.domain.auth.utils

data class ValidationPassResult(
    val correctLength: Boolean,
    val containSymbol: Boolean,
    val containMayusMinusNumber: Boolean,
)
