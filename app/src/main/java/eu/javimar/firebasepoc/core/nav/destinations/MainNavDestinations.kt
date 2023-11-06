package eu.javimar.firebasepoc.core.nav.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import eu.javimar.firebasepoc.core.nav.screens.BottomGraphScreens
import eu.javimar.firebasepoc.features.profile.ProfileMain
import eu.javimar.firebasepoc.features.storage.otherfiles.OtherFilesMain
import eu.javimar.firebasepoc.features.storage.training.TrainingMain

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
        route = BottomGraphScreens.OtherFiles.route
    ) {
        OtherFilesMain(
            navController = navController
        )
    }
}

fun NavGraphBuilder.trainingDestination(
    navController: NavHostController
) {
    composable(
        route = BottomGraphScreens.Training.route
    ) {
        TrainingMain(
            navController = navController
        )
    }
}
