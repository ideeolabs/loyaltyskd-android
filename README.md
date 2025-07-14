# Loyalty SDK - Guía de Implementación pa Android

## 📌 Visión General

**LoyaltySDK** es un SDK modular en formato `XCFramework`. Este repositorio incluye:

- ✅ `.aar` precompilado (`LoyaltySDK.xcframework`)
- ✅ Proyecto de ejemplo (`Demo_App/`)
- ✅ Documentación completa de integración

## 🚀 Requisitos Técnicos

- Kotlin: 2.1.0
- API LVL: 35
- Gradle: 8.7
- Android Gradle Plugin: 8.6.1
- JDK: 17


## 📦 Instalación

### Opción 1: .aar Local
1. Descarga el archivo `loyalty-sdk-release.aar`
2. Colócalo en la carpeta `app/libs/` de tu proyecto. Si no esta creada , mse debe de crear en `app` en la vista `Project`
3. Agrega la dependencia en tu `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation(files("libs/loyalty-sdk-release.aar"))
}
```

## 🚀 Implementación

### Views Tradicionales(XML)

#### 1. Importar el SDK
```kotlin
import com.convergence.loyaltysdk.LoyaltySDK
```

#### 2. Configurar el SDK(por ejemplo en `onCreate` )
```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar LoyaltySDK
        LoyaltySDK.configure(
            this,
            "test_FJiceIdJtRJbyiwkDa+vxG+xpfVwoLg8q4EhfCDZPF4="
        ) { success, error ->
            sdkConfigured = success
        }
    }
```

#### 3. Llamar al Método `loyaltyView(context: Context, token: String)` )
```kotlin
    val loyaltyView = LoyaltySDK.loyaltyView().loyaltyView(
        this,
        token = "hAh#8#Fvcyta86ac="
        ) {
            // Callback cuando la vista carga
            Toast.makeText(this, "View cargado", Toast.LENGTH_SHORT).show()
        }
```

#### 4. Layout XML
Crear el contenedor (FrameLayout/ViewGroup) donde se carga LoyaltyView
```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!-- Contenedor para LoyaltyView -->
    <FrameLayout
        android:id="@+id/contentContainer"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

    <!-- Otros componentes -->
    ...
</LinearLayout>
```

## 📋 Permisos Requeridos

Agrega estos permisos en tu `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 🔍 Ejemplo de Uso
La aplicación de referencia ubicada en `Demo_App` muestra cómo:

- Importar el SDK

- Configurar e implementar el SDK

- Usar callback para detectar eventos

## ▶️ Ejecutar la demo

- Abrir `Demo_App` en Android Studio

- Seleccionar un simulador o dispositivo físico

- Ejecutar y navegar entre las pestañas

## 📞 Soporte

Para soporte técnico o preguntas, contacta a:
- Email: jose.soto@ideeo.mx 

## 📄 Licencia

Este SDK está bajo licencia propietaria de Ideeolabs.
