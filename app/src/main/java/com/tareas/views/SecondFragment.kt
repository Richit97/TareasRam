package com.tareas.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tareas.database.TareaDatabase
import com.tareas.databinding.FragmentSecondBinding
import com.tareas.models.Tarea
import com.tareas.viewModels.TareasViewModels

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var database:TareaDatabase
    lateinit var viewmodel: TareasViewModels
    lateinit var tarea:Tarea

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProvider(requireActivity())[TareasViewModels::class.java]
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = TareaDatabase.getDatabase(requireContext())
        binding.btnAgregar.setOnClickListener {
            if (arguments?.getSerializable("TAREA") != null){
                tarea = arguments?.getSerializable("TAREA") as Tarea
                if (binding.edtDescripcion.text.toString().trim().isNotEmpty()){
                    viewmodel.updateTarea(tarea.id!!.toInt(),binding.edtDescripcion.text.toString(),tarea.completado)
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(requireContext(),"No hay tarea para guardar.Verifica", Toast.LENGTH_SHORT).show()
                }
            }else{
                nuevaTarea()
            }
        }
        if (arguments?.getSerializable("TAREA") != null){
            tarea = arguments?.getSerializable("TAREA") as Tarea
            binding.btnAgregar.text = "Guardar"
            binding.edtDescripcion.setText(tarea.descripcion)
        }
    }

    private fun nuevaTarea(){
        if (binding.edtDescripcion.text.toString().trim().isNotEmpty()){
            tarea = Tarea(null, binding.edtDescripcion.text.toString(), false)
            viewmodel.insertTarea(tarea)
            findNavController().popBackStack()
        }else{
            Toast.makeText(requireContext(),"No hay tarea para guardar.Verifica", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}