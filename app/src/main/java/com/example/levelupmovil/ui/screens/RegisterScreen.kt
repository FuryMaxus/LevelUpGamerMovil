package com.example.levelupmovil.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupmovil.viewmodel.RegisterViewModel



@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel
){
    val estado by viewModel.estado.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Crea Tu Cuenta LEVEL UP GAMER")

        OutlinedTextField(
            value = estado.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Nombre") },
            isError = estado.errors.name != null,
            singleLine = true,
            supportingText = {
                estado.errors.name?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth().testTag("reg_name")
        )


        OutlinedTextField(
            value = estado.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("E-mail") },
            isError = estado.errors.email != null,
            singleLine = true,
            supportingText = {
                estado.errors.email?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth().testTag("reg_email")
        )

        OutlinedTextField(
            value = estado.address,
            onValueChange = viewModel::onAddressChange ,
            label = { Text("Dirección de envío") },
            modifier = Modifier.fillMaxWidth().testTag("reg_address"),
            singleLine = true
        )

        OutlinedTextField(
            value = estado.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Contraseña") },
            isError = estado.errors.password != null,
            visualTransformation = PasswordVisualTransformation(),
            supportingText = {
                estado.errors.password?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth().testTag("reg_pass")
        )


        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = viewModel::onAceptaTerminosChange,
                modifier = Modifier.testTag("reg_terms")
            )
            Spacer(Modifier.width(8.dp))
            Text("Acepto los términos y condiciones")
        }

        Button(
            onClick = {
                viewModel.registrarUsuario(
                    onSuccess = onRegisterSuccess
                )
            },
            modifier = Modifier.fillMaxWidth().testTag("reg_button")
        ) {
            Text("Registrarse")
        }

        TextButton(onClick = onLoginClick) {
            Text("¿Ya tienes cuenta? Inicia Sesión.")
        }
    }

}