package eu.javimar.firebasepoc.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.core.utils.UIEvent
import eu.javimar.firebasepoc.features.auth.utils.GoogleAuthManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val googleAuthManager: GoogleAuthManager,
): ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    init {
        val userData = googleAuthManager.getSignedInUser()
        if(userData != null) {
            state = state.copy(
                user = userData
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
            googleAuthManager.signOut()
            sendUiEvent(UIEvent.Navigate(AuthGraphScreens.Login.route))
        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}