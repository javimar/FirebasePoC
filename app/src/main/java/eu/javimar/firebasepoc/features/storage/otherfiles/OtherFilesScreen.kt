package eu.javimar.firebasepoc.features.storage.otherfiles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.javimar.firebasepoc.core.components.LoadWebUrl
import eu.javimar.firebasepoc.core.components.rememberLifecycleEvent
import eu.javimar.firebasepoc.features.BottomBar
import eu.javimar.firebasepoc.features.storage.components.FileItem
import eu.javimar.firebasepoc.features.storage.state.StorageEvent
import eu.javimar.firebasepoc.features.storage.state.StorageState

@Composable
fun OtherFilesScreen(
    state: StorageState,
    onEvent: (StorageEvent) -> Unit,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                items(state.gallery) { fileInfo ->
                    FileItem(
                        fileInfo = fileInfo,
                        onEventClick = { uri ->
                            onEvent(StorageEvent.FileClick(uri))
                        }
                    )
                }
            }
        }

        val lifecycleEvent = rememberLifecycleEvent()
        LaunchedEffect(lifecycleEvent) {
            if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
                onEvent(StorageEvent.FileClick(""))
            }
        }

        if(state.fileUri.isNotBlank()) {
            LoadWebUrl(state.fileUri)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoragePreview() {
    OtherFilesScreen(
        state = StorageState(),
        onEvent = {},
        navController = rememberNavController(),
        snackbarHostState = SnackbarHostState()
    )
}