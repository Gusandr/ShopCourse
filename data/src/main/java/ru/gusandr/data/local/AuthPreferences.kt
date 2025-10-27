package ru.gusandr.data.local

import android.content.Context

class AuthPreferences(
    private val context: Context
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean, email: String) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            if (email.isNotBlank()) {
                putString(KEY_USER_EMAIL, email)
            }
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getSavedEmail(): String {
        return prefs.getString(KEY_USER_EMAIL, null)?:""
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    private companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_EMAIL = "user_email"
    }
}