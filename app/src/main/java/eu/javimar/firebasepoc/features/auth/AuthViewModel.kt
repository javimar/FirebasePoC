package eu.javimar.firebasepoc.features.auth

import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.javimar.firebasepoc.core.nav.screens.BottomGraphScreens
import eu.javimar.firebasepoc.core.utils.UIEvent
import eu.javimar.firebasepoc.core.utils.UIText
import eu.javimar.firebasepoc.features.auth.utils.GoogleAuthUiClient
import eu.javimar.firebasepoc.features.auth.utils.SignInResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient
): ViewModel() {

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    init {
        if(googleAuthUiClient.getSignedInUser() != null) {
            sendUiEvent(UIEvent.Navigate(BottomGraphScreens.Profile.route))
        }
    }

    fun onEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.SignInClicked -> {
                viewModelScope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    event.launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }

            is SignInEvent.SignIntent -> {
                viewModelScope.launch {
                    val signInResult = googleAuthUiClient.getSignWithIntent(
                        intent = event.intent ?: return@launch
                    )
                    onSignInResult(signInResult)
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