package com.test.androiddemoapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.convergence.loyaltysdk.LoyaltySDK
import com.test.androiddemoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var sdkConfigured = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar LoyaltySDK
        LoyaltySDK.configure(
            this,
            "test_FJiceIdJtRJbyiwkDa+vxG+xpfVwoLg8q4EhfCDZPF4="
        ) { success, error ->
            sdkConfigured = success
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_loyalty -> {
                    if (sdkConfigured) {
                        //Flujo Loyalty
                        showLoyaltyView()
                    } else {
                        Toast.makeText(this, "SDK no está listo", Toast.LENGTH_SHORT).show()
                    }
                    true
                }

                else -> false
            }
        }

        binding.bottomNavigation.selectedItemId = R.id.nav_home
    }

    private fun showLoyaltyView() {
        try {
            // Limpiar vista anterior
            binding.contentContainer.removeAllViews()

            // Crear Loyaltyview
            val loyaltyView = LoyaltySDK.loyaltyView(
                this,
                token = "hAh#8#Fvcyta86ac="
            ) {
                // Callback cuando la vista carga
                Toast.makeText(this, "View cargado", Toast.LENGTH_SHORT).show()
            }
            binding.contentContainer.addView(loyaltyView)

        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}