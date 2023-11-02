package eu.javimar.firebasepoc.features

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import eu.javimar.firebasepoc.core.nav.HomeNavGraph

@Composable
fun HomeMain(
    navController: NavHostController = rememberNavController(),
) {
    HomeNavGraph(navController = navController)
}