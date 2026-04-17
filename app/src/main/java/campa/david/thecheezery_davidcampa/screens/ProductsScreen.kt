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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import campa.david.thecheezery_davidcampa.R
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.domain.productList

@Composable
fun ProductsScreen(
    products: List<Product>,
    onProductClick: (Product) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Products", textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineSmall)

        LazyColumn(Modifier.fillMaxWidth()) {
            items(products) { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onProductClick(product) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    product.image?.let { imageRes ->
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = product.name,
                            modifier = Modifier.size(100.dp)
                        )
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
@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview(){
    ProductsScreen(products = productList, onProductClick = {})
}
