package eu.javimar.firebasepoc.features.auth.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import eu.javimar.firebasepoc.core.nav.screens.FORGOT_DEST
import eu.javimar.firebasepoc.core.nav.screens.SIGNUP_DEST
import eu.javimar.firebasepoc.core.utils.UIEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginMain(
    onAuth: () -> Unit,
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.event.collectLatest { event ->
            when(event) {
                is UIEvent.Navigate -> {
                    when {
                        event.route.contains(SIGNUP_DEST) ||
                        event.route.contains(FORGOT_DEST) ->  onNavigate(event)
                        else -> onAuth()
                    }
                }
                is UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                    )
                }
                else -> Unit
            }
        }
    }

    LoginScreen(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState
    )
}
