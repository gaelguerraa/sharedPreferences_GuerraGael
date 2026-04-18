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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import campa.david.thecheezery_davidcampa.data.DatabaseHelper
import campa.david.thecheezery_davidcampa.data.ProductDAO
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.domain.productList
import campa.david.thecheezery_davidcampa.screens.CarritoScreen
import campa.david.thecheezery_davidcampa.screens.LoginScreen
import campa.david.thecheezery_davidcampa.screens.ProductDetailScreen
import campa.david.thecheezery_davidcampa.screens.ProductsScreen
import campa.david.thecheezery_davidcampa.ui.theme.TheCheezery_DavidCampaTheme

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import campa.david.thecheezery_davidcampa.data.DataStoreManager
import kotlinx.coroutines.launch

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
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStoreManager = remember { DataStoreManager(context) }
    val dbHelper = remember { DatabaseHelper(context) }
    val productDAO = remember { ProductDAO(dbHelper) }

    val isLoggedIn by dataStoreManager.isLoggedInFlow.collectAsState(initial = false)
    
    var currentScreen by rememberSaveable { mutableStateOf("products") }

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            if (!isLoggedIn) {
                LoginScreen(onLoginSuccess = { username ->
                    scope.launch {
                        dataStoreManager.saveSession(username)
                    }
                })
            } else {
                when (currentScreen) {
                    "products" -> {
                        ProductsScreen(
                            productDAO = productDAO,
                            onCartClick = {
                                currentScreen = "cart"
                            },
                            onLogoutClick = {
                                scope.launch {
                                    dataStoreManager.logout()
                                }
                            }
                        )
                    }
                    "cart" -> {
                        CarritoScreen(
                            productDAO = productDAO,
                            onBack = { currentScreen = "products" }
                        )
                    }
                }
            }
        }
    }
}