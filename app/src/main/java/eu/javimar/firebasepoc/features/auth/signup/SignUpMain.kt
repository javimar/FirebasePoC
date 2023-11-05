package eu.javimar.firebasepoc.features.auth.signup

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import eu.javimar.firebasepoc.core.utils.UIEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpMain(
    onPopBackStack: () -> Unit,
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.event.collectLatest { event ->
            when(event) {
                is UIEvent.Navigate -> {
                    onNavigate(event)
                }
                is UIEvent.PopBackStack -> {
                    onPopBackStack()
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

    SignUpScreen(
        state = viewModel.state,
        buttonState = viewModel.buttonEnabledState,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState
    )
}
