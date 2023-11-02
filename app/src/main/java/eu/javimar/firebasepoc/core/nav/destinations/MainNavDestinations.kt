package eu.javimar.firebasepoc.core.nav.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import eu.javimar.firebasepoc.core.nav.screens.BottomGraphScreens
import eu.javimar.firebasepoc.features.profile.ProfileMain
import eu.javimar.firebasepoc.features.storage.StorageScreen

fun NavGraphBuilder.profileDestination(
    navController: NavHostController
) {
    composable(
        route = BottomGraphScreens.Profile.route,
    ) {
        ProfileMain(
            navController = navController
        )
    }
}

fun NavGraphBuilder.storageDestination(
    navController: NavHostController
) {
    composable(
        route = BottomGraphScreens.Storage.route
    ) {
        StorageScreen()
    }
}
