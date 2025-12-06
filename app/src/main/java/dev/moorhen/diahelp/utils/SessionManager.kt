package dev.moorhen.diahelp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_COEFF_INSULIN = "coeffInsulin"

    }

    fun saveUser(username: String, email: String, coeffInsulin: Double) {
        prefs.edit().apply {
            putString(KEY_USERNAME, username)
            putString(KEY_EMAIL, email)
            putFloat(KEY_COEFF_INSULIN, coeffInsulin.toFloat())
            apply()
        }
    }

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)
    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)
    fun getCoeffInsulin(): Double {
        return prefs.getFloat(KEY_COEFF_INSULIN, 0f).toDouble()
    }


    fun isLoggedIn(): Boolean = getUsername() != null

    fun logout() {
        prefs.edit().clear().apply()
    }
}
