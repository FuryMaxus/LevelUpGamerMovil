package com.example.levelupmovil.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.levelupmovil.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_EMAIL_KEY = stringPreferencesKey("USER_EMAIL")
        val USER_PASSWORD = stringPreferencesKey("USER_PASSWORD")
        val USER_PIC_URI_KEY = stringPreferencesKey("USER_PIC_URI")
    }

    suspend fun saveUserData(userData: UserData) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = userData.name
            preferences[USER_EMAIL_KEY] = userData.email
            preferences[USER_PASSWORD] = userData.password
            preferences[USER_PIC_URI_KEY] = ""
        }
    }
    val userDataFlow: Flow<UserData> = context.dataStore.data.map { preferences ->
        UserData(
            name = preferences[USER_NAME_KEY] ?: "",
            email = preferences[USER_EMAIL_KEY] ?: "",
            password = preferences[USER_PASSWORD] ?: "",
            profilePicUri = preferences[USER_PIC_URI_KEY] ?: ""
        )
    }

    suspend fun updateUserProfilePic(uri: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_PIC_URI_KEY] = uri
        }
    }
}