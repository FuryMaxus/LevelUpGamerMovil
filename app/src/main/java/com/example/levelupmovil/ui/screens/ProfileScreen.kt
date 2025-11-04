package com.example.levelupmovil.ui.screens

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.levelupmovil.viewmodel.ProfileViewModel
import android.net.Uri
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import com.example.levelupmovil.ui.components.ImagenInteligente



@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit,
){
    val userData by viewModel.userData.collectAsState()
    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                viewModel.saveProfilePicture(context, uri)
            }
        }
    )

    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { exito: Boolean ->
            viewModel.onFotoTomada(context, exito)
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                val uriTemp = viewModel.getUriParaTomarFoto(context)
                launcherCamara.launch(uriTemp)
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ImagenInteligente(uri = userData.profilePicUri)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = {
                photoPickerLauncher.launch("image/*")
            }
        ) {
            Text("Seleccionar de Galería")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        ) {
            Icon(Icons.Default.CameraAlt, contentDescription = "Tomar foto")
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text("Tomar Foto")
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (userData.email.isEmpty()) {
            CircularProgressIndicator()
        } else {
            Text(
                text = "¡Bienvenido, ${userData.name}!",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sesión iniciada como:",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = userData.email,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onLogout) {
                Text("Cerrar Sesión")
            }
        }


    }
}

