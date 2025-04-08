import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val KEY_AUTH_TOKEN = "auth_token"
        const val KEY_USER_ID = "user_id"
    }

    fun saveAuthToken(token: String) {
        sharedPref.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(): String? {
        return sharedPref.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearSession() {
        sharedPref.edit().clear().apply()
    }
}