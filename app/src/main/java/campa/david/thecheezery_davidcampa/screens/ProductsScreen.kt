package campa.david.thecheezery_davidcampa.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import campa.david.thecheezery_davidcampa.R
import campa.david.thecheezery_davidcampa.data.ProductDAO
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.domain.productList

@Composable
fun ProductsScreen(
    productDAO: ProductDAO,
    onCartClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var products by remember { mutableStateOf(listOf<Product>()) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        products = productDAO.getAllProducts()
    }

    if (selectedProduct != null) {
        ProductDetailScreen(
            product = selectedProduct!!,
            onAddToCart = {
                scope.launch {
                    productDAO.addToCart(selectedProduct!!.id)
                }
            },
            onBack = { selectedProduct = null }
        )
    } else {
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
                Button(onClick = onLogoutClick) {
                    Text("Cerrar Sesión")
                }
                Text(
                    "Products",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(onClick = onCartClick) {
                    Text("Carrito")
                }
            }

            LazyColumn(Modifier.fillMaxWidth()) {
                items(products) { product ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedProduct = product }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        product.image?.let { imageName ->
                            val imageId = context.resources.getIdentifier(
                                imageName,
                                "drawable",
                                context.packageName
                            )
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
                            Text(
                                text = product.descriptor ?: "",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(text = "$${product.price}", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    }
}

fun abrirCarritoScreen(){
    // Lógica para abrir la pantalla del carrito
}


@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview(){
    // ProductsScreen(products = productList, onProductClick = {}, onCartClick = {}, onLogoutClick = {})
}
