package jp.cordea.midstream.ui.login

import android.content.Context
import android.preference.PreferenceManager

class KeyManager(context: Context) {
    companion object {
        private const val ACCOUNT_NAME = "account_name"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var accountName: String
        get() = preferences.getString(ACCOUNT_NAME, "")
        set(value) {
            preferences.edit().putString(ACCOUNT_NAME, value).apply()
        }
}
