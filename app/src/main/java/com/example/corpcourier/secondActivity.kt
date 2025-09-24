package com.example.corpcourier

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class secondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nombre = intent.getStringExtra("nombre")
        val usuario = intent.getStringExtra("usuario")

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = "Bienvenido, $nombre ($usuario)"

        val btnBack = findViewById< ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

            val cardNueva = findViewById<CardView>(R.id.cardNueva)
        cardNueva.setOnClickListener {
            val intent = Intent(this, RegistrationForm::class.java)
            startActivity(intent)
        }
    }
}

