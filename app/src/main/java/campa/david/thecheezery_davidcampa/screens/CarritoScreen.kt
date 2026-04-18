package campa.david.thecheezery_davidcampa.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import campa.david.thecheezery_davidcampa.domain.Product

@Composable
fun CarritoScreen(
    products: List<Product>,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBack) {
                Text("⬅ Volver")
            }
            Text(
                "Carrito",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.width(64.dp)) // To center the title
        }

        LazyColumn(Modifier.fillMaxWidth()) {
            items(products) { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    product.image?.let { imageName ->
                        val imageId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
                        if (imageId != 0) {
                            Image(
                                painter = painterResource(id = imageId),
                                contentDescription = product.name,
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = product.descriptor ?: "", style = MaterialTheme.typography.bodySmall)
                    }
                    Text(text = "$${product.price}", style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    }
}
