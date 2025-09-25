package com.example.corpcourier

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.corpcourier.databinding.ItemSolicitudBinding

class SolicitudesAdapter(
    private val solicitudes: MutableList<Solicitud>,
    private val listener: OnMenuClickListener
) : RecyclerView.Adapter<SolicitudesAdapter.SolicitudViewHolder>() {

    interface OnMenuClickListener {
        fun onEditClick(solicitud: Solicitud)
        fun onDeleteClick(solicitud: Solicitud)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitudViewHolder {
        val binding = ItemSolicitudBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SolicitudViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SolicitudViewHolder, position: Int) {
        val solicitud = solicitudes[position]
        holder.bind(solicitud)
    }

    override fun getItemCount(): Int = solicitudes.size

    inner class SolicitudViewHolder(private val binding: ItemSolicitudBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(solicitud: Solicitud) {
            binding.txtNombre.text = solicitud.nombre
            binding.txtTelefono.text = "Teléfono: ${solicitud.telefono}"
            binding.txtDireccion.text = "Dirección: ${solicitud.direccion}"
            binding.txtMunicipio.text = "Municipio: ${solicitud.municipio}"
            binding.txtDescripcion.text = "Descripción: ${solicitud.descripcion}"

            binding.btnMenu.setOnClickListener {
                showPopupMenu(it, solicitud)
            }
        }

        private fun showPopupMenu(view: android.view.View, solicitud: Solicitud) {
            val popup = PopupMenu(view.context, view)
            popup.menuInflater.inflate(R.menu.solicitud_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        listener.onEditClick(solicitud)
                        true
                    }

                    R.id.action_delete -> {
                        listener.onDeleteClick(solicitud)
                        true
                    }

                    else -> false
                }
            }
            popup.show()
        }
    }

}