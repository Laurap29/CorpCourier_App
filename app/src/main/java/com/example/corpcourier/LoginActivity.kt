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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val campoUsuario = findViewById<EditText>(R.id.campoUsuario)
        val campoContrasena = findViewById<EditText>(R.id.campoContrasena)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, registrationActivity::class.java)
            startActivity(intent)
        }

        btnIngresar.setOnClickListener {
            val usuario = campoUsuario.text.toString().trim()
            val contrasena = campoContrasena.text.toString().trim()

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                if (usuario == "test@correo.com" && contrasena == "1234") {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, secondActivity::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()

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

