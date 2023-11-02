package eu.javimar.firebasepoc.core.nav.screens

sealed class AuthGraphScreens(
    val route: String,
) {
    data object Auth: AuthGraphScreens(route = AUTH_DEST)
}
const val AUTH_DEST = "auth"
