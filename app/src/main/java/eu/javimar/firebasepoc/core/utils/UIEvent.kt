package eu.javimar.firebasepoc.core.utils

import androidx.compose.material3.SnackbarDuration

sealed class UIEvent {
    data object PopBackStack: UIEvent()
    data object DismissSnackbar: UIEvent()
    data class Navigate(val route: String): UIEvent()
    data class ShowSnackbar(
        val message: UIText,
        val action: UIText? = null,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ): UIEvent()
}

