package com.tareas.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tareas.databinding.ItemTareasBinding
import com.tareas.models.Tarea

class AdaptadorTareas(
    private val mContexto: Context?,
    private var data: List<Tarea>,
    private var mListener: OnItemClickListenerTarea
): RecyclerView.Adapter<AdaptadorTareas.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemTareasBinding.inflate(LayoutInflater.from(mContexto), viewGroup, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position].let { holder.bind(it) }
    }

    inner class ViewHolder(var itemview: ItemTareasBinding) : RecyclerView.ViewHolder(itemview.root) {

        fun bind(modelo: Tarea) {
            itemview.edtDescripcion.text = modelo.descripcion
            itemview.cbCompletado.isChecked = modelo.completado
            itemview.edtDescripcion.setOnClickListener {
                mListener.onItemClick(modelo)
            }
            itemview.cbCompletado.setOnCheckedChangeListener { compoundButton, b ->
                mListener.update(modelo,adapterPosition,itemview.cbCompletado.isChecked)
            }
            itemview.imbDelete.setOnClickListener {
                mListener.onClickDelete(modelo,adapterPosition)
            }
        }

    }
    interface OnItemClickListenerTarea{
        fun onItemClick(modelo: Tarea)
        fun onClickDelete(modelo: Tarea,position: Int)
        fun update(modelo: Tarea,position: Int,isChecked: Boolean)
    }

}