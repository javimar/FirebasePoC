package eu.javimar.firebasepoc.core.nav.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import eu.javimar.firebasepoc.core.nav.Graph
import eu.javimar.firebasepoc.core.nav.screens.AuthGraphScreens
import eu.javimar.firebasepoc.features.auth.forgotpass.ForgotPassMain
import eu.javimar.firebasepoc.features.auth.login.LoginMain
import eu.javimar.firebasepoc.features.auth.signup.SignUpMain

fun NavGraphBuilder.loginDestination(
    navController: NavHostController
) {
    composable(
        route = AuthGraphScreens.Login.route
    ) {
        LoginMain(
            onAuth = {
                navController.navigate(Graph.PROFILE) {
                    popUpTo(Graph.AUTH) {
                        inclusive = true
                    }
                }
            },
            onNavigate = {
                navController.navigate(it.route)
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
        ForgotPassMain(
            onNavigate = {
                navController.navigate(it.route)
            },
            onPopBackStack = {
                navController.popBackStack()
            }
        )
    }
}

fun NavGraphBuilder.signUpDestination(
    navController: NavHostController
) {
    composable(
        route = AuthGraphScreens.SignUp.route
    ) {
        SignUpMain(
            onNavigate = {
                navController.navigate(it.route)
            },
            onPopBackStack = {
                navController.popBackStack()
            }
        )
    }
}