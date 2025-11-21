package com.example.levelupmovil.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.levelupmovil.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PIC_URI = stringPreferencesKey("user_pic_uri")
        val USER_ADDRESS = stringPreferencesKey("user_address")
    }

    private var cachedToken: String? = null
    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN]
    }.onEach { token ->
        cachedToken = token
    }

    val userData: Flow<UserData> = dataStore.data.map { preferences ->
        UserData(
            name = preferences[USER_NAME] ?: "",
            email = preferences[USER_EMAIL] ?: "",
            password = "",
            profilePicUri = preferences[USER_PIC_URI] ?: "",
            address = preferences[USER_ADDRESS] ?: "Sin dirección"
        )
    }

    fun getTokenInstant(): String? {
        return cachedToken ?: runBlocking {
            dataStore.data.map { it[AUTH_TOKEN] }.first()
        }
    }

    suspend fun saveToken(token: String) {
        cachedToken = token
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }
    suspend fun saveAuthData(token: String, name: String, email: String, address: String?) {
        cachedToken = token
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
            preferences[USER_NAME] = name
            preferences[USER_EMAIL] = email
            preferences[USER_ADDRESS] = address ?: ""
        }
    }

    suspend fun updateUserProfilePic(uri: String) {
        dataStore.edit { preferences ->
            preferences[USER_PIC_URI] = uri
        }
    }

    suspend fun clearAuthData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}