package eu.javimar.firebasepoc.features.auth.login.state

sealed interface LoginEvent {
    data class EmailChanged(val email: String): LoginEvent
    data class PasswordChanged(val password: String): LoginEvent
    data object GuestLogin: LoginEvent
    data object UserPassLogin: LoginEvent
    data object Register: LoginEvent
    data object ForgotPass: LoginEvent
}