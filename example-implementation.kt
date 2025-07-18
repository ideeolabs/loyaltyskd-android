// Ejemplo de implementación del Loyalty SDK
  
import com.ideeolabs.loyalty.sdk.LoyaltySDK
import com.ideeolabs.loyalty.sdk.LoyaltyConfig
import kotlinx.coroutines.launch

// ========================================
// EJEMPLO 1: IMPLEMENTACIÓN CON JETPACK COMPOSE
// ========================================

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

// ========================================
// EJEMPLO 2: IMPLEMENTACIÓN CON VIEWS TRADICIONALES
// ========================================

package com.ideeolabs.loyaltyviews

import android.os.Bundle 
import androidx.appcompat.app.AppCompatActivity 
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import com.ideeolabs.loyalty.sdk.LoyaltySDK
import com.ideeolabs.loyalty.sdk.LoyaltyConfig
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var loyaltyContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loyaltyContainer = findViewById(R.id.loyalty_container)

        // Configurar el SDK
        configureLoyaltySDK()

        // Configurar botones del footer
        setupFooterButtons()
    }

    private fun configureLoyaltySDK() {
        lifecycleScope.launch {
            try {
                val success = LoyaltySDK.configure(
                    context = this@MainActivity,
                    config = LoyaltyConfig(
                        appKey = "test_FJiceIdJtRJbyiwkDa+vxG+xpfVwoLg8q4EhfCDZPF4="
                    )
                )

                if (success) {
                    println("SDK configurado exitosamente")
                    setupLoyaltyWebView()
                }
            } catch (e: Exception) {
                println("Error configurando SDK: ${e.message}")
            }
        }
    }

    private fun setupLoyaltyWebView() {
        val webView = WebView(this).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    println("Loyalty SDK cargado")
                }
            }
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
                mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            // Agregar interfaz JavaScript para comunicación
            addJavascriptInterface(object {
                @android.webkit.JavascriptInterface
                fun sendMessageToAndroid(message: String) {
                    println("Mensaje desde JavaScript: $message")
                }

                @android.webkit.JavascriptInterface
                fun getAndroidData(): String {
                    return "{\"status\": \"success\", \"data\": \"from Android\"}"
                }
            }, "AndroidInterface")
        }

        // Cargar la URL del SDK
        val apiResponse = LoyaltySDK.getConfigureResponse()
        val urlToLoad = if (apiResponse?.error == 0) {
            apiResponse.webViewUrl
        } else {
            "https://banorte.com"
        }

        webView.loadUrl(urlToLoad.toString())

        // Agregar el WebView al contenedor
        loyaltyContainer.addView(webView)
    }

    private fun setupFooterButtons() {
        findViewById<Button>(R.id.btnHome).setOnClickListener {
            // Navegar a Home
        }

        findViewById<Button>(R.id.btnPagos).setOnClickListener {
            // Navegar a Pagos
        }

        findViewById<Button>(R.id.btnFinanzas).setOnClickListener {
            // Navegar a Finanzas
        }

        findViewById<Button>(R.id.btnLealtad).setOnClickListener {
            // Navegar a Lealtad
        }

        findViewById<Button>(R.id.btnDescubre).setOnClickListener {
            // Navegar a Descubre
        }
    }

}

// Layout XML correspondiente (activity_main.xml):
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!-- Header -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFCDD2"
        android:padding="16dp">

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Loyalty Program"
            android:textSize="20sp"
            android:textStyle="bold" />

        <ImageButton
            android:id="@+id/btnNotifications"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_notifications"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:contentDescription="Notifications" />

        <ImageButton
            android:id="@+id/btnInfo"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_info"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:contentDescription="Info" />

    </LinearLayout>

    <!-- Contenedor del WebView -->
    <FrameLayout
        android:id="@+id/loyalty_container"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

    <!-- Footer -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFCDD2"
        android:padding="8dp">

        <Button
            android:id="@+id/btnHome"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Home"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

        <Button
            android:id="@+id/btnPagos"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Pagos"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

        <Button
            android:id="@+id/btnFinanzas"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Finanzas"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

        <Button
            android:id="@+id/btnLealtad"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Lealtad"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

        <Button
            android:id="@+id/btnDescubre"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Descubre"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

    </LinearLayout>

</LinearLayout>