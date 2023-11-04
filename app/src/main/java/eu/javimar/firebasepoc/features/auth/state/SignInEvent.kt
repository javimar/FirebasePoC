package eu.javimar.firebasepoc.features.auth.state

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest

sealed interface SignInEvent {
    data class SignInClicked(val launcher:  ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>):
        SignInEvent
    data class SignIntent(val intent: Intent?): SignInEvent
    data object GuestLogin: SignInEvent
}