package com.satyasetu.app.util;

import android.content.Context;
import android.content.res.Configuration;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.Flow;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;

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
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0007J\u000e\u0010\b\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0007J\u0018\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00140\u0013J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/satyasetu/app/util/LocaleManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "currentLanguage", "Lkotlinx/coroutines/flow/Flow;", "", "getCurrentLanguage", "()Lkotlinx/coroutines/flow/Flow;", "languageKey", "Landroidx/datastore/preferences/core/Preferences$Key;", "applyLocale", "baseContext", "languageCode", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLanguageDisplayName", "code", "getSupportedLanguages", "", "Lkotlin/Pair;", "setLanguage", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class LocaleManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> languageKey = null;
    
    /**
     * Observe the current language as a Flow.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> currentLanguage = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LANG_HINDI = "hi";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LANG_ENGLISH = "en";
    @org.jetbrains.annotations.NotNull()
    public static final com.satyasetu.app.util.LocaleManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public LocaleManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Observe the current language as a Flow.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getCurrentLanguage() {
        return null;
    }
    
    /**
     * Get current language synchronously (blocking, use sparingly).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCurrentLanguage(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Set the app language and persist it.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String languageCode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Apply the saved locale to a Context.
     * Call this in Activity.attachBaseContext().
     */
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context applyLocale(@org.jetbrains.annotations.NotNull()
    android.content.Context baseContext, @org.jetbrains.annotations.NotNull()
    java.lang.String languageCode) {
        return null;
    }
    
    /**
     * Get the display name for a language code.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLanguageDisplayName(@org.jetbrains.annotations.NotNull()
    java.lang.String code) {
        return null;
    }
    
    /**
     * Get all supported languages.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> getSupportedLanguages() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/satyasetu/app/util/LocaleManager$Companion;", "", "()V", "LANG_ENGLISH", "", "LANG_HINDI", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}