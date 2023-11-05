package eu.javimar.firebasepoc.features.auth.signup

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.javimar.coachpoc.R
import eu.javimar.domain.auth.usecases.ValidateConfirmPassUseCase
import eu.javimar.domain.auth.usecases.ValidateEmailUseCase
import eu.javimar.domain.auth.usecases.ValidatePassUseCase
import eu.javimar.domain.auth.utils.AuthRes
import eu.javimar.domain.auth.utils.ConfirmPassChecker
import eu.javimar.firebasepoc.core.loge
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.core.utils.UIEvent
import eu.javimar.firebasepoc.core.utils.UIText
import eu.javimar.firebasepoc.features.auth.signup.state.SignUpEvent
import eu.javimar.firebasepoc.features.auth.signup.state.SignUpState
import eu.javimar.firebasepoc.features.auth.utils.GoogleAuthManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val googleAuthManager: GoogleAuthManager,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePassUseCase: ValidatePassUseCase,
    private val validateConfirmPassUseCase: ValidateConfirmPassUseCase
): ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    val buttonEnabledState by derivedStateOf {
        state.email.isNotBlank() &&
        state.password.isNotBlank() &&
        state.confirmPassword.isNotBlank() &&
        passwordChecked()
    }

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> state = state.copy(email = event.email)
            is SignUpEvent.RegisterClicked -> checkValidation()
            is SignUpEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                checkPassword(event.password)
            }
            is SignUpEvent.ConfirmPasswordChanged -> state = state.copy(confirmPassword = event.password)
            SignUpEvent.AlreadyAccount -> {
                sendUiEvent((UIEvent.Navigate(AuthGraphScreens.Login.route)))
            }
        }
    }

    private fun checkPassword(password: String) {
        viewModelScope.launch {
            val passValidation = validatePassUseCase.execute(password)
            state = state.copy(
                checkPassLength = passValidation.correctLength,
                checkPassSymbol = passValidation.containSymbol,
                checkPassMayusMinusNumber = passValidation.containMayusMinusNumber
            )
        }
    }

    private fun createAccount() {
        viewModelScope.launch {
            when(val result = googleAuthManager.createUserWithEmailAndPassword(state.email, state.password)) {
                is AuthRes.Success -> {
                    sendUiEvent(UIEvent.ShowSnackbar(UIText.StringResource(R.string.signup_form_create_ok)))
                    sendUiEvent(UIEvent.PopBackStack)
                }
                is AuthRes.Error -> {
                    loge(result.errorMessage)
                    sendUiEvent(UIEvent.ShowSnackbar(UIText.StringResource(R.string.signup_form_create_error)))
                }
            }
        }
    }

    private fun checkValidation() {
        viewModelScope.launch {
            resetErrors()

            val confirmPassResult = validateConfirmPassUseCase
                .execute(ConfirmPassChecker(state.password, state.confirmPassword))
            val emailResult = validateEmailUseCase.execute(state.email)

            val hasError = listOf(
                confirmPassResult,
                emailResult,
            ).any { !it.successful }

            if(hasError) {
                state = state.copy(
                    confirmPasswordError = confirmPassResult.errorMessage,
                    emailError = emailResult.errorMessage
                )
                return@launch
            }
            if(passwordChecked()) createAccount()
        }
    }

    private fun passwordChecked(): Boolean {
        return state.checkPassMayusMinusNumber &&
                state.checkPassLength &&
                state.checkPassSymbol
    }

    private fun resetErrors() {
        state = state.copy(
            confirmPasswordError = null,
            emailError = null
        )
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}