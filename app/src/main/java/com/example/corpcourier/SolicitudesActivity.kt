package com.example.corpcourier

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corpcourier.databinding.ActivitySolicitudesBinding
import com.google.android.material.card.MaterialCardView

class SolicitudesActivity : AppCompatActivity(), SolicitudesAdapter.OnMenuClickListener {

    private lateinit var binding: ActivitySolicitudesBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var solicitudesAdapter: SolicitudesAdapter
    private var solicitudes: MutableList<Solicitud> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitudesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        // Configurar el RecyclerView
        setupRecyclerView()

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        solicitudes.addAll(dbHelper.getAllSolicitudes())
        solicitudesAdapter = SolicitudesAdapter(solicitudes, this)
        binding.recyclerViewSolicitudes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSolicitudes.adapter = solicitudesAdapter
    }

    // Función para refrescar los datos de la base de datos
    private fun refreshData() {
        solicitudes.clear()
        solicitudes.addAll(dbHelper.getAllSolicitudes())
        solicitudesAdapter.notifyDataSetChanged()
    }

    override fun onEditClick(solicitud: Solicitud) {
        val intent = Intent(this, RegistrationForm::class.java).apply {
            // Pasa los datos de la solicitud
            putExtra("solicitud_id", solicitud.id)
            putExtra("nombre", solicitud.nombre)
            putExtra("telefono", solicitud.telefono)
            putExtra("direccion", solicitud.direccion)
            putExtra("municipio", solicitud.municipio)
            putExtra("descripcion", solicitud.descripcion)
        }
        startActivity(intent)
    }

    override fun onDeleteClick(solicitud: Solicitud) {
        // Implementar la logica para eliminar
        dbHelper.deleteSolicitud(solicitud.id)

        // Luego refresca la lista
        refreshData()
        Toast.makeText(this, "Eliminar solicitud: ${solicitud.nombre}", Toast.LENGTH_SHORT).show()
    }
}
