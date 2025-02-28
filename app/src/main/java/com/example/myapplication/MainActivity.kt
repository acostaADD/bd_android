package com.example.myapplication

import android.widget.Toast

import android.widget.EditText
import android.widget.Button
import android.view.View
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Declarar las vistas
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addButton: Button
    private lateinit var dbHelper: DBHelper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar las vistas
        nameEditText = findViewById(R.id.nameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addButton = findViewById(R.id.addButton)

        // Inicializar la base de datos
        dbHelper = DBHelper(this)

        // Configurar el OnClickListener para el botón "Agregar Contacto"
        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                // Agregar el contacto a la base de datos
                val isInserted = dbHelper.addContact(name, phone)

                // Mostrar un mensaje dependiendo del resultado
                if (isInserted) {
                    Toast.makeText(this, "Contacto agregado correctamente", Toast.LENGTH_SHORT).show()
                    // Limpiar los campos después de agregar el contacto
                    nameEditText.text.clear()
                    phoneEditText.text.clear()
                } else {
                    Toast.makeText(this, "Error al agregar contacto", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show()
            }
        }






        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}