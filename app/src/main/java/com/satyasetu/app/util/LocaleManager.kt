package com.satyasetu.app.util

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

internal val Context.localeDataStore by preferencesDataStore(name = "locale_prefs")

/**
 * LocaleManager - Handles runtime language switching.
 *
 * Supports:
 * - Hindi (hi) - Default
 * - English (en)
 *
 * Persists user preference using DataStore.
 * Applies locale at Activity configuration level.
 */
@Singleton
class LocaleManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val languageKey = stringPreferencesKey("app_language")

    /**
     * Observe the current language as a Flow.
     */
    val currentLanguage: Flow<String> = context.localeDataStore.data
        .map { preferences ->
            preferences[languageKey] ?: LANG_HINDI
        }

    /**
     * Get current language synchronously (blocking, use sparingly).
     */
    suspend fun getCurrentLanguage(): String {
        return context.localeDataStore.data
            .map { it[languageKey] ?: LANG_HINDI }
            .first()
    }

    /**
     * Set the app language and persist it.
     */
    suspend fun setLanguage(languageCode: String) {
        context.localeDataStore.edit { preferences ->
            preferences[languageKey] = languageCode
        }
    }

    /**
     * Apply the saved locale to a Context.
     * Call this in Activity.attachBaseContext().
     */
    fun applyLocale(baseContext: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(baseContext.resources.configuration)
        config.setLocale(locale)

        return baseContext.createConfigurationContext(config)
    }

    /**
     * Get the display name for a language code.
     */
    fun getLanguageDisplayName(code: String): String = when (code) {
        LANG_HINDI -> "हिंदी"
        LANG_ENGLISH -> "English"
        else -> code
    }

    /**
     * Get all supported languages.
     */
    fun getSupportedLanguages(): List<Pair<String, String>> = listOf(
        LANG_HINDI to "हिंदी",
        LANG_ENGLISH to "English"
    )

    companion object {
        const val LANG_HINDI = "hi"
        const val LANG_ENGLISH = "en"
    }
}
