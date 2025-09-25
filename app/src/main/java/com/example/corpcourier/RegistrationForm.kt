package com.example.corpcourier

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.corpcourier.databinding.ActivityRegistrationFormBinding

class RegistrationForm : ComponentActivity() {

    private lateinit var binding: ActivityRegistrationFormBinding
    private lateinit var dbHelper: DBHelper
    private var solicitudId: Int = -1 // Variable to store the request's ID for editing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        // Check for incoming request data to handle editing
        val intent = intent
        solicitudId = intent.getIntExtra("solicitud_id", -1)

        if (solicitudId != -1) {
            // Edit mode: populate fields and change button text
            binding.etNombre.setText(intent.getStringExtra("nombre"))
            binding.etTelefono.setText(intent.getStringExtra("telefono"))
            binding.etDireccion.setText(intent.getStringExtra("direccion"))
            binding.etMunicipio.setText(intent.getStringExtra("municipio"))
            binding.etDescripcion.setText(intent.getStringExtra("descripcion"))
            binding.btnGuardar.text = "ACTUALIZAR"
        } else {
            // New request mode: set button text for saving
            binding.btnGuardar.text = "GUARDAR"
        }

        binding.btnGuardar.setOnClickListener {
            // Determine whether to update or save based on the request ID
            if (solicitudId != -1) {
                updateSolicitud()
            } else {
                saveSolicitud()
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun saveSolicitud() {
        val nombre = binding.etNombre.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()
        val direccion = binding.etDireccion.text.toString().trim()
        val municipio = binding.etMunicipio.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()

        if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && municipio.isNotEmpty() && descripcion.isNotEmpty()) {
            val solicitud = Solicitud(
                nombre = nombre,
                telefono = telefono,
                direccion = direccion,
                municipio = municipio,
                descripcion = descripcion
            )

            val success = dbHelper.addSolicitud(solicitud)
            if (success) {
                Toast.makeText(this, "Solicitud guardada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar la solicitud", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSolicitud() {
        val nombre = binding.etNombre.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()
        val direccion = binding.etDireccion.text.toString().trim()
        val municipio = binding.etMunicipio.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()

        if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && municipio.isNotEmpty() && descripcion.isNotEmpty()) {
            val solicitud = Solicitud(
                id = solicitudId,
                nombre = nombre,
                telefono = telefono,
                direccion = direccion,
                municipio = municipio,
                descripcion = descripcion
            )

            val success = dbHelper.updateSolicitud(solicitud)
            if (success > 0) {
                Toast.makeText(this, "Solicitud actualizada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar la solicitud", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
