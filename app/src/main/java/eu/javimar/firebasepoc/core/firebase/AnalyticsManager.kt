package eu.javimar.firebasepoc.core.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import eu.javimar.domain.analytics.model.MyCustomAnalyticsEvent
import eu.javimar.domain.analytics.model.MyCustomAnalyticsParam

class AnalyticsManager(context: Context) {

    private val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    private fun logEvent(eventName: String, params: Bundle) {
        firebaseAnalytics.logEvent(eventName, params)
    }

    fun buttonClicked(buttonName: String) {
        val params = Bundle().apply {
            putString(MyCustomAnalyticsParam.BUTTON_NAME, buttonName)
        }
        logEvent(MyCustomAnalyticsEvent.BUTTON_CLICKED, params)
    }

    fun logScreenView(screenName: String) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        }
        logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }

    fun logError(errorName: String) {
        val params = Bundle().apply {
            putString(MyCustomAnalyticsParam.ERROR_NAME, errorName)
        }
        logEvent(MyCustomAnalyticsEvent.ERRORS_ISSUED, params)
    }
}
