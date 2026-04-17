package campa.david.thecheezery_davidcampa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import campa.david.thecheezery_davidcampa.domain.obtenerProductoPorId
import campa.david.thecheezery_davidcampa.domain.productList
import campa.david.thecheezery_davidcampa.screens.ProductDetailScreen
import campa.david.thecheezery_davidcampa.screens.ProductsScreen
import campa.david.thecheezery_davidcampa.ui.theme.TheCheezery_DavidCampaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheCheezery_DavidCampaTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    var selectedProductId by rememberSaveable { mutableIntStateOf(-1) }

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {

            if (selectedProductId == -1) {
                ProductsScreen(
                    products = productList,
                    onProductClick = { product ->
                        selectedProductId = product.id
                    }
                )
            } else {
                val selectedProduct = obtenerProductoPorId(selectedProductId)

                if (selectedProduct != null) {
                    ProductDetailScreen(
                        product = selectedProduct,
                        onAddToCart = {
                            // TODO: lógica de carrito
                        },
                        onBack = {
                            selectedProductId = -1
                        }
                    )
                } else {
                    ProductsScreen(
                        products = productList,
                        onProductClick = { product ->
                            selectedProductId = product.id
                        }
                    )
                }
            }
        }
    }
}