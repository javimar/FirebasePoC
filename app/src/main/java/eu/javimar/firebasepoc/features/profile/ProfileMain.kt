package eu.javimar.firebasepoc.features.profile

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.javimar.firebasepoc.core.nav.Graph
import eu.javimar.firebasepoc.core.utils.UIEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileMain(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(context) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    navController.navigate(Graph.AUTH) {
                        popUpTo(Graph.PROFILE) {
                            inclusive = true
                        }
                    }
                }
                is UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    ProfileScreen(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        navController = navController
    )
}