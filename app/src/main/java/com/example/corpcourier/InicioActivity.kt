package com.example.corpcourier

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val campoUsuario = findViewById<EditText>(R.id.campoUsuario)
        val campoContrasena = findViewById<EditText>(R.id.campoContrasena)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        // Navegar al registro
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, registrationActivity::class.java)
            startActivity(intent)
        }

        // Ingresar
        btnIngresar.setOnClickListener {
            val usuario = campoUsuario.text.toString().trim()
            val contrasena = campoContrasena.text.toString().trim()

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val dbHelper = DBHelper(this)

                // Primero, verifica si el usuario existe.
                if (dbHelper.userExists(usuario)) {
                    // El usuario existe, ahora valida la contraseña.
                    if (dbHelper.validateUser(usuario, contrasena)) {

                        val intent = Intent(this, SecondActivity::class.java)
                        intent.putExtra("usuario", usuario)
                        startActivity(intent)

                        // Limpiar campos.
                        campoUsuario.text.clear()
                        campoContrasena.text.clear()
                    } else {
                        // Contraseña incorrecta.
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // El usuario no existe en la base de datos.
                    Toast.makeText(this, "El usuario no existe. Por favor, regístrate.", Toast.LENGTH_LONG).show()
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}