package com.tareas.database

import androidx.lifecycle.LiveData
import com.tareas.models.Tarea

class TareasRepository(private val tareaDao: TareaDao) {

    val allTareas : LiveData<List<Tarea>> = tareaDao.getAllTareas()

    suspend fun insert(tarea: Tarea){
        tareaDao.insert(tarea)
    }
    suspend fun update(id:Int,descripcion:String,completado:Boolean){
        tareaDao.update(id,descripcion,completado)
    }
    suspend fun delete(id:Int){
        tareaDao.delete(id)
    }
}