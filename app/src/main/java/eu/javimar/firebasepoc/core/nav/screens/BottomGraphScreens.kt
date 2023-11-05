package eu.javimar.firebasepoc.core.nav.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import eu.javimar.coachpoc.R

sealed class BottomGraphScreens(
    val route: String,
    @StringRes val resourceId: Int = 0,
    @DrawableRes val icon: Int,
) {
    data object Profile: BottomGraphScreens(route = PROFILE_DEST, R.string.nav_profile, R.drawable.ic_profile)
    data object Storage: BottomGraphScreens(route = STORAGE_DEST, R.string.nav_storage, R.drawable.ic_cloud)
}

const val PROFILE_DEST = "profile_dest"
const val STORAGE_DEST = "storage_dest"
