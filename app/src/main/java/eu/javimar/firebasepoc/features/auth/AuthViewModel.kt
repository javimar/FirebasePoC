package eu.javimar.firebasepoc.features.auth

import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.javimar.firebasepoc.core.loge
import eu.javimar.firebasepoc.core.nav.screens.BottomGraphScreens
import eu.javimar.firebasepoc.core.utils.UIEvent
import eu.javimar.firebasepoc.core.utils.UIText
import eu.javimar.firebasepoc.features.auth.state.SignInEvent
import eu.javimar.firebasepoc.features.auth.state.SignInState
import eu.javimar.firebasepoc.features.auth.utils.AuthRes
import eu.javimar.firebasepoc.features.auth.utils.GoogleAuthManager
import eu.javimar.firebasepoc.features.auth.utils.SignInResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val googleAuthManager: GoogleAuthManager
): ViewModel() {

    var state by mutableStateOf(SignInState())
        private set

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    init {
        if(googleAuthManager.getSignedInUser() != null) {
            sendUiEvent(UIEvent.Navigate(BottomGraphScreens.Profile.route))
        }
    }

    fun onEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.SignInClicked -> {
                viewModelScope.launch {
                    val signInIntentSender = googleAuthManager.signIn()
                    event.launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }

            is SignInEvent.SignIntent -> {
                viewModelScope.launch {
                    val signInResult = googleAuthManager.getSignWithIntent(
                        intent = event.intent ?: return@launch
                    )
                    onSignInResult(signInResult)
                }
            }

            SignInEvent.GuestLogin -> loginInAnonymous()
        }
    }

    private fun loginInAnonymous() {
        viewModelScope.launch {
            when(val result = googleAuthManager.signInAnonymously()) {
                is AuthRes.Success -> {
                    sendUiEvent(UIEvent.Navigate(BottomGraphScreens.Profile.route))
                }
                is AuthRes.Error -> {
                    UIEvent.ShowSnackbar(
                        message = UIText.DynamicString("Error SignIn Anonymous"),
                    )
                    loge(result.errorMessage)
                }
            }
        }
    }

    private fun onSignInResult(result: SignInResult) {
        if (result.data != null) {
            sendUiEvent(UIEvent.Navigate(BottomGraphScreens.Profile.route))
        } else {
            sendUiEvent(
                UIEvent.ShowSnackbar(
                    message = UIText.DynamicString("Sign In Error"),
                )
            )
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}