package eu.javimar.firebasepoc.core.nav.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import eu.javimar.firebasepoc.core.nav.Graph
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.features.auth.LoginMain

fun NavGraphBuilder.loginDestination(
    navController: NavHostController
) {
    composable(
        route = AuthGraphScreens.Login.route
    ) {
        LoginMain(
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

fun NavGraphBuilder.forgotPassDestination(
    navController: NavHostController
) {
    composable(
        route = AuthGraphScreens.ForgotPass.route
    ) {

    }
}

fun NavGraphBuilder.signUpDestination(
    navController: NavHostController
) {
    composable(
        route = AuthGraphScreens.SignUp.route
    ) {

    }
}