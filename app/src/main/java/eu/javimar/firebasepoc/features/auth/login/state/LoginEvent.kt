package eu.javimar.firebasepoc.features.auth.login.state

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest

sealed interface LoginEvent {
    data class GoogleLogin(val launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>): LoginEvent
    data class GoogleSignIntent(val intent: Intent?): LoginEvent
    data class EmailChanged(val email: String): LoginEvent
    data class PasswordChanged(val password: String): LoginEvent
    data object GuestLogin: LoginEvent
    data object UserPassLogin: LoginEvent
    data object SignUp: LoginEvent
    data object ForgotPass: LoginEvent
}