package eu.javimar.firebasepoc.features.auth.forgotpass

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.javimar.coachpoc.R
import eu.javimar.domain.auth.usecases.ValidateEmailUseCase
import eu.javimar.domain.auth.utils.FileResult
import eu.javimar.firebasepoc.core.firebase.AnalyticsManager
import eu.javimar.firebasepoc.core.firebase.GoogleAuthManager
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.core.utils.UIEvent
import eu.javimar.firebasepoc.core.utils.UIText
import eu.javimar.firebasepoc.features.auth.forgotpass.state.ForgotPassEvent
import eu.javimar.firebasepoc.features.auth.forgotpass.state.ForgotPassState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPassViewModel @Inject constructor(
    private val googleAuthManager: GoogleAuthManager,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val analyticsManager: AnalyticsManager,
): ViewModel() {

    var state by mutableStateOf(ForgotPassState())
        private set

    val buttonState by derivedStateOf {
        state.email.isNotBlank()
    }

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    init {
        analyticsManager.logScreenView(AuthGraphScreens.ForgotPass.route)
    }

    fun onEvent(event: ForgotPassEvent) {
        when(event) {
            ForgotPassEvent.RecoverClicked -> checkValidation()
            is ForgotPassEvent.EmailChanged -> state = state.copy(email = event.email)
        }
    }

    private fun checkValidation() {
        viewModelScope.launch {
            resetErrors()
            val emailResult = validateEmailUseCase.execute(state.email)
            val hasError = listOf(emailResult).any { !it.successful }
            if(hasError) {
                state = state.copy(emailError = emailResult.errorMessage)
                return@launch
            }
            resetPassword()
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            when(val result = googleAuthManager.resetPassword(state.email)) {
                is FileResult.Success -> {
                    analyticsManager.buttonClicked("Click: Reset password OK -> ${state.email}")
                    sendUiEvent(UIEvent.ShowSnackbar(message = UIText.StringResource(R.string.signup_form_recover_pass_email_sent)))
                    delay(1500)
                    sendUiEvent(UIEvent.PopBackStack)
                }
                is FileResult.Error -> {
                    analyticsManager.logError("Error Reset password: ${result.errorMessage}")
                    sendUiEvent(UIEvent.ShowSnackbar(message = UIText.DynamicString(result.errorMessage)))
                }
            }
        }
    }

    private fun resetErrors() {
        state = state.copy(
            emailError = null
        )
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}