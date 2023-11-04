package eu.javimar.firebasepoc.core.nav.screens

sealed class AuthGraphScreens(
    val route: String,
) {
    data object Login: AuthGraphScreens(route = LOGIN_DEST)
    data object ForgotPass: AuthGraphScreens(route = FORGOT_DEST)
    data object SignUp: AuthGraphScreens(route = SIGNUP_DEST)
}

const val LOGIN_DEST = "login_dest"
const val SIGNUP_DEST = "signup_dest"
const val FORGOT_DEST = "forgot_pass_dest"
