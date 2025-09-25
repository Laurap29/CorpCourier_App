package com.example.corpcourier

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usuario = intent.getStringExtra("usuario") ?: "Invitado"

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = "Bienvenido, $usuario"

        val cardSolicitudes = findViewById<CardView>(R.id.cardSolicitudes)
        cardSolicitudes.setOnClickListener {
            val intent = Intent(this, SolicitudesActivity::class.java)
            startActivity(intent)
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        val cardNueva = findViewById<CardView>(R.id.cardNueva)
        cardNueva.setOnClickListener {
            val intent = Intent(this, RegistrationForm::class.java)
            startActivity(intent)
        }
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        btnCerrarSesion.setOnClickListener {
            // Limpia la sesión guardada en SharedPreferences
            val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
            prefs.edit().clear().apply()

            // Volver a la pantalla de login (InicioActivity)
            val intent = Intent(this, InicioActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
