package campa.david.thecheezery_davidcampa.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment


@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit){
    var user by remember{mutableStateOf("")}
    var pass by remember { mutableStateOf("")}
    var error by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(36.dp),
        Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text("Inicio de sesión", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = user, onValueChange = {user = it; error = false},
            label = { Text("Nombre de usuario")},
            modifier = Modifier.fillMaxWidth(),
            isError = error
        )
        OutlinedTextField(value = pass, onValueChange = {pass = it; error = false},
            label = {Text("Contraseña")},
            modifier = Modifier.fillMaxWidth(),
            isError = error
        )

        if (error) {
            Text(
                text = "Usuario o contraseña incorrectos",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(Modifier.height(30.dp))
        Button(
            onClick = {
                if (user == "admin" && pass == "1234") {
                    onLoginSuccess(user)
                } else {
                    error = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    LoginScreen(onLoginSuccess = {})
}