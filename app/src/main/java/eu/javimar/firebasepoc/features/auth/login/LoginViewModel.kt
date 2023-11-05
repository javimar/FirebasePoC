package eu.javimar.firebasepoc.features.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.javimar.coachpoc.R
import eu.javimar.domain.auth.utils.AuthRes
import eu.javimar.firebasepoc.core.loge
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.core.nav.screens.BottomGraphScreens
import eu.javimar.firebasepoc.core.utils.UIEvent
import eu.javimar.firebasepoc.core.utils.UIText
import eu.javimar.firebasepoc.features.auth.login.state.LoginEvent
import eu.javimar.firebasepoc.features.auth.login.state.LoginState
import eu.javimar.firebasepoc.features.auth.utils.GoogleAuthManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleAuthManager: GoogleAuthManager,
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    init {
        if(googleAuthManager.getSignedInUser() != null) {
            sendUiEvent(UIEvent.Navigate(BottomGraphScreens.Profile.route))
        }
    }

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EmailChanged -> state = state.copy(email = event.email)
            is LoginEvent.PasswordChanged -> state = state.copy(password = event.password)
            LoginEvent.Register -> {
                sendUiEvent((UIEvent.Navigate(AuthGraphScreens.SignUp.route)))
            }
            LoginEvent.GuestLogin -> loginInAnonymous()
            LoginEvent.UserPassLogin -> loginUserPass()
            LoginEvent.ForgotPass -> sendUiEvent((UIEvent.Navigate(AuthGraphScreens.ForgotPass.route)))
        }
    }

    private fun loginUserPass() {
        viewModelScope.launch {
            when(val result = googleAuthManager.signInWithEmailAndPassword(
                state.email, state.password
            )) {
                is AuthRes.Success -> {
                    sendUiEvent(UIEvent.Navigate(BottomGraphScreens.Profile.route))
                }
                is AuthRes.Error -> {
                    UIEvent.ShowSnackbar(
                        message = UIText.StringResource(R.string.signup_form_login_error),
                    )
                    loge(result.errorMessage)
                }
            }
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
                        message = UIText.StringResource(R.string.signup_form_login_error),
                    )
                    loge(result.errorMessage)
                }
            }
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}