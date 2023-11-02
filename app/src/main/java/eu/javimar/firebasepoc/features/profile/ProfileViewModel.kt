package eu.javimar.firebasepoc.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.core.utils.UIEvent
import eu.javimar.firebasepoc.features.auth.utils.GoogleAuthUiClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient,
): ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    init {
        val userData = googleAuthUiClient.getSignedInUser()
        if(userData != null) {
            state = state.copy(
                userData = userData
            )
        }
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            ProfileEvent.SignOut -> signOut()
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            sendUiEvent(UIEvent.Navigate(AuthGraphScreens.Auth.route))
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}