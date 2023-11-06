package eu.javimar.firebasepoc.core.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import eu.javimar.firebasepoc.core.nav.destinations.forgotPassDestination
import eu.javimar.firebasepoc.core.nav.destinations.loginDestination
import eu.javimar.firebasepoc.core.nav.destinations.profileDestination
import eu.javimar.firebasepoc.core.nav.destinations.signUpDestination
import eu.javimar.firebasepoc.core.nav.destinations.storageDestination
import eu.javimar.firebasepoc.core.nav.destinations.trainingDestination
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.core.nav.screens.BottomGraphScreens
import eu.javimar.firebasepoc.features.HomeMain

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Graph.AUTH,
        route = Graph.ROOT
    ) {
        authNavGraph(navHostController)
        composable(
            route = Graph.PROFILE
        ) {
            HomeMain()
        }
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthGraphScreens.Login.route
    ) {
        loginDestination(navController)
        forgotPassDestination(navController)
        signUpDestination(navController)
    }
}

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.PROFILE,
        startDestination = BottomGraphScreens.Profile.route

    ) {
        profileDestination(navController)
        storageDestination(navController)
        trainingDestination(navController)
        authNavGraph(navController)
    }
}