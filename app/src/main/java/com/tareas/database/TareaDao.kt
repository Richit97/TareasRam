package com.tareas.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tareas.models.Tarea

@Dao
interface TareaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tarea: Tarea)

    @Query("Select * from tareas_table order by id ASC")
    fun getAllTareas() : LiveData<List<Tarea>>

    @Query("UPDATE tareas_table Set descripcion = :descripcion," +
            "completado = :completado WHERE id = :id")
    suspend fun update(id:Int?,descripcion:String?,completado:Boolean?)

    @Query("DELETE FROM tareas_table WHERE id = :id")
    suspend fun delete(id: Int?)

}