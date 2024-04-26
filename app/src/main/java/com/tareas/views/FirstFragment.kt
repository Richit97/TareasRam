package com.tareas.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tareas.R
import com.tareas.databinding.FragmentFirstBinding
import com.tareas.models.Tarea
import com.tareas.viewModels.TareasViewModels
import java.io.Serializable

class FirstFragment : Fragment(), AdaptadorTareas.OnItemClickListenerTarea {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: TareasViewModels
    private var mContext: Context? = null
    var adapterTareas: AdaptadorTareas? = null
    var listaCompletados= arrayListOf<Tarea>()
    var listaNocompletados = arrayListOf<Tarea>()
    var lista = listOf<Tarea>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[TareasViewModels::class.java]
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.SecondFragment)
        }
        binding.linearFiltro.setOnClickListener {
            popupShare(binding.linearFiltro)
        }
        viewModel.allTareas.observe(viewLifecycleOwner){list ->
            lista = list
            completados(list)
            rvTareas(lista)
        }
    }

    private fun completados(data:List<Tarea>){
        listaCompletados.clear()
        listaNocompletados.clear()
        data.forEachIndexed { index, tarea ->
            if (tarea.completado){
                listaCompletados.add(tarea)
            }else{
                listaNocompletados.add(tarea)
            }
        }
    }

    private fun rvTareas(data:List<Tarea>){
        if (data.isEmpty()) binding.txtInfo.visibility = View.VISIBLE else binding.txtInfo.visibility = View.GONE
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapterTareas = AdaptadorTareas(requireContext(),data,this@FirstFragment)
            adapter = adapterTareas
        }
    }

    private fun popupShare(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.menu_main, menu)
        setForceShowIcon(true)
        setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.action_completados ->{
                    rvTareas(listaCompletados)
                }
                R.id.action_nocompletos ->{
                    rvTareas(listaNocompletados)
                }
                R.id.action_todos ->{
                    rvTareas(lista)
                }
            }
            true
        }
        show()
    }

    override fun onItemClick(modelo: Tarea) {
        val bundle = Bundle()
        bundle.putSerializable("TAREA",modelo as Serializable)
        findNavController().navigate(R.id.SecondFragment,bundle)
    }

    override fun onClickDelete(modelo: Tarea, position: Int) {
        viewModel.deleteTarea(modelo.id!!)
        adapterTareas?.notifyItemRemoved(position)
    }

    override fun update(modelo: Tarea, position: Int, isChecked: Boolean) {
        viewModel.updateTarea(modelo.id!!.toInt(),modelo.descripcion,isChecked)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}