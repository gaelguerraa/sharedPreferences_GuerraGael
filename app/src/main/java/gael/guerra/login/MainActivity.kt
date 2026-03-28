package gael.guerra.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gael.guerra.login.model.Producto
import gael.guerra.login.ui.theme.LoginTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = PreferenceManager(this)

        enableEdgeToEdge()
        setContent {

            var screenState by remember {
                mutableStateOf(if (prefs.isLoggedIn()) "HOME" else "LOGIN")
            }

            val carrito = remember {
                mutableStateListOf<Producto>().apply {
                    addAll(prefs.getCarrito())
                }
            }

            if (screenState == "LOGIN") {
                LoginScreen(
                    onLoginClick = {
                        prefs.saveLoginStatus(true)
                        screenState = "HOME"
                    }
                )
            } else {
                HomeScreen(
                    onLogoutClick = {
                        prefs.logout()
                        screenState = "LOGIN"
                    },
                    carrito = carrito,
                    prefs = prefs
                )
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = ""
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email == "admin@mail.com" && password == "1234") {
                    onLoginClick()
                } else {
                    errorMessage = "Credenciales incorrectas. Intenta de nuevo."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }
    }
}

@Composable
fun HomeScreen(
    onLogoutClick: () -> Unit,
    carrito: MutableList<Producto>,
    prefs: PreferenceManager
) {

    var pantalla by remember { mutableStateOf("PRODUCTOS") }
    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { onLogoutClick() }) {
                Text("Cerrar sesión")
            }
        }

        when (pantalla) {

            "PRODUCTOS" -> ProductosScreen(
                onCarritoClick = { pantalla = "CARRITO" },
                onAgregarCarrito = {
                    carrito.add(it)
                    prefs.saveCarrito(carrito)
                },
                onProductoClick = {
                    productoSeleccionado = it
                    pantalla = "DETALLE"
                }
            )

            "CARRITO" -> CarritoScreen(
                carrito = carrito,
                onBack = { pantalla = "PRODUCTOS" }
            )

            "DETALLE" -> {
                productoSeleccionado?.let {
                    DetalleProductoScreen(
                        productoId = it.id,
                        onAgregarCarrito = { prod ->
                            carrito.add(prod)
                            prefs.saveCarrito(carrito)
                        },
                        onBack = { pantalla = "PRODUCTOS" }
                    )
                }
            }
        }
    }
}