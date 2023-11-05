package eu.javimar.firebasepoc.features.auth.signup.state

sealed interface SignUpEvent {
    data object RegisterClicked: SignUpEvent
    data class EmailChanged(val email: String): SignUpEvent
    data class PasswordChanged(val password: String): SignUpEvent
    data class ConfirmPasswordChanged(val password: String): SignUpEvent
    data object AlreadyAccount: SignUpEvent

}