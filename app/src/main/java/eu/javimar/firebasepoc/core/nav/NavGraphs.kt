package eu.javimar.firebasepoc.core.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import eu.javimar.firebasepoc.core.nav.destinations.profileDestination
import eu.javimar.firebasepoc.core.nav.destinations.storageDestination
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.core.nav.screens.BottomGraphScreens
import eu.javimar.firebasepoc.features.HomeMain
import eu.javimar.firebasepoc.features.auth.AuthMain

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
        startDestination = AuthGraphScreens.Auth.route
    ) {
        composable(
            route = AuthGraphScreens.Auth.route,
        ) {
            AuthMain(
                onNavigate = {
                    navController.navigate(Graph.PROFILE) {
                        popUpTo(Graph.AUTH) {
                            inclusive = true
                        }
                    }
                }
            )
        }
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
        authNavGraph(navController)
    }
}