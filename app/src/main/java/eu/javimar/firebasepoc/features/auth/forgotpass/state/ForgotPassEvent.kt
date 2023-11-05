package eu.javimar.firebasepoc.features.auth.forgotpass.state

sealed interface ForgotPassEvent {
    data object RecoverClicked: ForgotPassEvent
    data class EmailChanged(val email: String): ForgotPassEvent
}