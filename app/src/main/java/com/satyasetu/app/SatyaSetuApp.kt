package com.satyasetu.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

import org.osmdroid.config.Configuration

@HiltAndroidApp
class SatyaSetuApp : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Initialize OpenStreetMap (Osmdroid)
        Configuration.getInstance().load(
            this,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        )

        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val generalChannel = NotificationChannel(
                CHANNEL_GENERAL,
                "सामान्य सूचनाएँ",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "SatyaSetu की सामान्य सूचनाएँ"
            }

            val learningChannel = NotificationChannel(
                CHANNEL_LEARNING,
                "सीखने की सूचनाएँ",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "नए पाठ और क्विज़ की सूचनाएँ"
            }

            val reportChannel = NotificationChannel(
                CHANNEL_REPORTS,
                "रिपोर्ट अपडेट",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "आपकी रिपोर्ट की स्थिति अपडेट"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannels(
                listOf(generalChannel, learningChannel, reportChannel)
            )
        }
    }

    companion object {
        const val CHANNEL_GENERAL = "satyasetu_general"
        const val CHANNEL_LEARNING = "satyasetu_learning"
        const val CHANNEL_REPORTS = "satyasetu_reports"
    }
}
