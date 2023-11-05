package eu.javimar

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp
import eu.javimar.coachpoc.R

@HiltAndroidApp
class FirebasePocApp: Application() {

    companion object {
        const val CHANNEL_NAME = "Firebase PoC channel"
    }

    override fun onCreate() {
        super.onCreate()
        manageFcm()
    }

    private fun manageFcm() {
        val channelId = this.getString(R.string.defaul_notification_channel_id)
        val channel = NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}