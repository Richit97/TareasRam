package com.tareas.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tareas.database.TareaDatabase
import com.tareas.database.TareasRepository
import com.tareas.models.Tarea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TareasViewModels(application: Application):AndroidViewModel(application) {

    private var repository:TareasRepository
    val allTareas:LiveData<List<Tarea>>

    init {
        var dao = TareaDatabase.getDatabase(application).getTareaDao()
        repository = TareasRepository(dao)
        allTareas = repository.allTareas
    }

    fun insertTarea(tarea: Tarea) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(tarea)
    }
    fun updateTarea(id:Int,descripcion:String,completado:Boolean) = viewModelScope.launch(Dispatchers.IO){
        repository.update(id,descripcion,completado)
    }
    fun deleteTarea(id:Int) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(id)
    }
}