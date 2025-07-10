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

### 1. Configurar el SDK
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

### 2. Mostrar el Componente
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
    "message": "No error. Registrado en ambiente de prueba"
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
- `configure(context, config)` - Inicializa el SDK
- `isInitialized()` - Verifica si está inicializado 
- `getConfigureResponse()` - Obtiene la respuesta de configuración
- `ShowViewController(tokenSAML, modifier)` - Muestra el componente, se tiene que pasar el tokenSAML

## 📞 Soporte

Para soporte técnico o preguntas, contacta a:
- Email: soporte@ideeolabs.com
- Documentación: https://docs.ideeolabs.com

## 📄 Licencia

Este SDK está bajo licencia propietaria de Ideeolabs. 
