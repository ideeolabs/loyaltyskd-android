# Loyalty SDK - Guía de Implementación

## 📦 Instalación

### Opción 1: AAR Local
1. Descarga el archivo `loyalty-sdk-release.aar`
2. Colócalo en la carpeta `app/libs/` de tu proyecto
3. Agrega la dependencia en tu `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation(files("libs/loyalty-sdk-release.aar"))
    
    // Dependencias requeridas
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
}
```

### Opción 2: Maven Repository (Próximamente)
```kotlin
dependencies {
    implementation("com.ideeolabs:loyalty-sdk:1.0.0")
}
```

## 🚀 Implementación

### Opción 1: Con Jetpack Compose

#### 1. Configurar el SDK
```kotlin
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
                    // SDK configurado exitosamente
                    println("SDK listo para usar")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}
```

#### 2. Mostrar el Componente
```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyLoyaltyView() {
    LoyaltySDK.ShowViewController(
        tokenSAML = "tu_token_saml",
        modifier = Modifier.fillMaxSize()
    )
}
```

### Opción 2: Con Views Tradicionales

#### 1. Configurar el SDK
```kotlin
import com.ideeolabs.loyalty.sdk.LoyaltySDK
import com.ideeolabs.loyalty.sdk.LoyaltyConfig
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        lifecycleScope.launch {
            try {
                val success = LoyaltySDK.configure(
                    context = this@MainActivity,
                    config = LoyaltyConfig(
                        appKey = "tu_app_key_aqui"
                    )
                )
                
                if (success) {
                    setupLoyaltyView()
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
    
    private fun setupLoyaltyView() {
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
            
            // Agregar interfaz JavaScript
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
        
        webView.loadUrl(urlToLoad)
        
        // Agregar el WebView al layout
        val container = findViewById<FrameLayout>(R.id.loyalty_container)
        container.addView(webView)
    }
}
```

#### 2. Layout XML
```xml
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
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/ic_notifications"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:contentDescription="Notifications" />

        <ImageButton
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
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Home"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

        <Button
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Pagos"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

        <Button
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Finanzas"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

        <Button
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Lealtad"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

        <Button
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Descubre"
            android:background="?attr/selectableItemBackground"
            android:textColor="@android:color/black" />

    </LinearLayout>

</LinearLayout>
```

## 📋 Permisos Requeridos

Agrega estos permisos en tu `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 🔧 Configuración

### LoyaltyConfig
```kotlin
data class LoyaltyConfig(
    val appKey: String,     // Tu clave de API
    val debug: Boolean?     // Modo debug (opcional)
)
```

## 📡 API Response

El SDK maneja automáticamente las respuestas de la API:

### Respuesta Exitosa
```json
{
    "error": 0,
    "status": "success",
    "message": "No error. Registrado en ambiente de prueba",
    "user_code": "2cadbycyd7",
    "environment": "pruebas",
    "web_view_url": "https://soto.com/home"
}
```

### Respuesta con Error
```json
{
    "error": 1,
    "status": "error",
    "message": "AppKey inválido o no autorizado"
}
```

## 🛠️ Métodos Disponibles

### LoyaltySDK
- `configure(context, config)` - Configura el SDK
- `isInitialized()` - Verifica si está inicializado
- `getCurrentConfig()` - Obtiene la configuración actual
- `getConfigureResponse()` - Obtiene la respuesta de configuración
- `ShowViewController(tokenSAML, modifier)` - Muestra el componente (Compose)

## 📞 Soporte

Para soporte técnico o preguntas, contacta a:
- Email: soporte@ideeolabs.com
- Documentación: https://docs.ideeolabs.com

## 📄 Licencia

Este SDK está bajo licencia propietaria de Ideeolabs.
