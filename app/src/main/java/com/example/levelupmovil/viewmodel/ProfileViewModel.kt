package com.example.levelupmovil.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.levelupmovil.LevelUpMovilApplication
import com.example.levelupmovil.data.model.UserData
import com.example.levelupmovil.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class ProfileViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    val userData: StateFlow<UserData> = userPreferencesRepository.userData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserData(name = "", email = "", password = "", profilePicUri = "", address = "")
    )

    private val _tempFotoUri = MutableStateFlow<Uri?>(null)

    fun getUriParaTomarFoto(context: Context): Uri {
        val archivo = crearArchivoImagen(context)
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            archivo
        )
        _tempFotoUri.value = uri
        return uri
    }

    fun onFotoTomada(context: Context, exito: Boolean) {
        if (exito) {
            _tempFotoUri.value?.let {
                saveProfilePicture(context, it)
            }
        }
        _tempFotoUri.value = null
    }

    fun saveProfilePicture(context: Context, uri: Uri) {
        viewModelScope.launch {
            val permanentUri = saveImageToInternalStorage(context, uri)

            if (permanentUri != null) {
                val uriString = permanentUri.toString()
                val cacheBustedUri = "$uriString?v=${System.currentTimeMillis()}"
                userPreferencesRepository.updateUserProfilePic(cacheBustedUri)
            }
        }
    }

    private fun saveImageToInternalStorage(context: Context, uri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)

            val file = File(context.filesDir, "profile_pic.jpg")
            val outputStream = FileOutputStream(file)

            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun crearArchivoImagen(context: Context): File {
        val directorio = context.filesDir
        val subDirectorio = File(directorio, "Pictures")
        if (!subDirectorio.exists()) {
            subDirectorio.mkdirs()
        }
        return File(subDirectorio, "profile_pic.jpg")
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LevelUpMovilApplication)
                ProfileViewModel(app.container.userPreferencesRepository)
            }
        }
    }
}