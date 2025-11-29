package com.example.levelupmovil.util

import android.util.Base64
import org.json.JSONObject

object JwtUtils {

    fun getRoleFromToken(token: String): String {
        try {
            val parts = token.split(".")
            if (parts.size < 2) return "ROL_CLIENTE"

            val payload = parts[1]
            val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)
            val decodedString = String(decodedBytes, Charsets.UTF_8)

            val jsonObject = JSONObject(decodedString)

            if (jsonObject.has("roles")) {
                val rolesArray = jsonObject.getJSONArray("roles")
                return rolesArray.getString(0)
            }
            return "ROL_CLIENTE"
        } catch (e: Exception) {
            e.printStackTrace()
            return "ROL_CLIENTE"
        }
    }
}
