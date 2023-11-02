package eu.javimar.firebasepoc.features.profile

sealed interface ProfileEvent {
    data object SignOut: ProfileEvent
}