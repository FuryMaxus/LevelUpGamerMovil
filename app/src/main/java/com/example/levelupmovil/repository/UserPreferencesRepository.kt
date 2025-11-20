package com.example.levelupmovil.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.levelupmovil.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PIC_URI = stringPreferencesKey("user_pic_uri")
    }
    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN]
    }

    val userData: Flow<UserData> = dataStore.data.map { preferences ->
        UserData(
            name = preferences[USER_NAME] ?: "",
            email = preferences[USER_EMAIL] ?: "",
            password = "",
            profilePicUri = preferences[USER_PIC_URI] ?: ""
        )
    }

    suspend fun saveAuthData(token: String, name: String, email: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = name
            preferences[USER_EMAIL] = email
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