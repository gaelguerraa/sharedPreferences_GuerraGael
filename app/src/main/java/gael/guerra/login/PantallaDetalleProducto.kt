package gael.guerra.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gael.guerra.login.model.Producto
import gael.guerra.login.model.obtenerProductoPorId

@Composable
fun DetalleProductoScreen(
    productoId: Int,
    onAgregarCarrito: (Producto) -> Unit,
    onBack: () -> Unit
) {
    val producto = obtenerProductoPorId(productoId)

    if (producto == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Producto no encontrado")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(onClick = { onBack() }) {
            Text("⬅ Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = producto.imagen),
            contentDescription = producto.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = producto.nombre,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$${producto.precio}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = producto.descripcion,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onAgregarCarrito(producto) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar al carrito 🛒")
        }
    }
}