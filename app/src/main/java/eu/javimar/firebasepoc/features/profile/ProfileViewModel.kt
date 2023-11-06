package eu.javimar.firebasepoc.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.javimar.firebasepoc.core.firebase.AnalyticsManager
import eu.javimar.firebasepoc.core.firebase.GoogleAuthManager
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.core.nav.screens.BottomGraphScreens
import eu.javimar.firebasepoc.core.utils.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val googleAuthManager: GoogleAuthManager,
    private val analyticsManager: AnalyticsManager,
): ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    private val _eventChannel = Channel<UIEvent>()
    val event = _eventChannel.receiveAsFlow()

    init {
        setProfileScreen()
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            ProfileEvent.SignOut -> signOut()
        }
    }

    private fun setProfileScreen() {
        viewModelScope.launch {
            analyticsManager.logScreenView(BottomGraphScreens.Profile.route)
            val userData = googleAuthManager.getSignedInUser()
            if(userData != null) {
                state = state.copy(
                    user = userData,
                    tokenInfo = googleAuthManager.getTokenInfo()
                )
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            analyticsManager
                .buttonClicked("Click: Logout: ${googleAuthManager.getSignedInUser()?.email}")
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