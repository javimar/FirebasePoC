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
    data object Training: BottomGraphScreens(route = TRAINING_DEST, R.string.nav_coach_storage, R.drawable.ic_swim)
    data object OtherFiles: BottomGraphScreens(route = OTHER_FILES_DEST, R.string.nav_other_storage, R.drawable.ic_files)
}

const val PROFILE_DEST = "profile_dest"
const val OTHER_FILES_DEST = "other_files_dest"
const val TRAINING_DEST = "training_dest"
