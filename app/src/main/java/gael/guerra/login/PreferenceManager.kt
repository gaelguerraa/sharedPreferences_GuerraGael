package gael.guerra.login

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gael.guerra.login.model.Producto

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit()
            .putBoolean("is_logged_in", isLoggedIn)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    fun logout() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }

    fun saveCarrito(carrito: List<Producto>) {
        val json = Gson().toJson(carrito)
        sharedPreferences.edit()
            .putString("carrito", json)
            .apply()
    }

    fun getCarrito(): MutableList<Producto> {
        val json = sharedPreferences.getString("carrito", null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Producto>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }
    }
}