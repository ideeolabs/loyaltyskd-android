// Ejemplo de implementación del Loyalty SDK

import com.ideeolabs.loyalty.sdk.LoyaltySDK
import com.ideeolabs.loyalty.sdk.LoyaltyConfig
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        lifecycleScope.launch {
            try {
                val success = LoyaltySDK.configure(
                    context = this@MainActivity,
                    config = LoyaltyConfig(
                        appKey = "tu_app_key_aqui"
                    )
                )
                
                if (success) {
                    println("SDK configurado exitosamente")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}

@Composable
fun MyLoyaltyView() {
    LoyaltySDK.ShowViewController(
        tokenSAML = "tu_token_saml",
        modifier = Modifier.fillMaxSize()
    )
}
