package campa.david.thecheezery_davidcampa.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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

@Composable
fun ProductsScreen(products: List<Product>){
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        Text("Products", textAlign = TextAlign.Center)
        LazyColumn(Modifier.fillMaxWidth()){
            items(products){
                product ->
                Row {
                    Image(painter = painterResource(R.drawable.ic_launcher_background), contentDescription = "product image")
                    Column() {
                        Text("${product.name}")
                        Text("${product.price}")
                    }
                    Text("$${product.price}")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview(){
    ProductsScreen()
}
