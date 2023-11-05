package eu.javimar.firebasepoc.features.auth.forgotpass.state

sealed interface ForgotPassEvent {
    data object RecoverClicked: ForgotPassEvent
}