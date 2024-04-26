package com.tareas.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas_table")
data class Tarea(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    @ColumnInfo(name = "descripcion") val descripcion:String,
    @ColumnInfo(name = "completado") var completado: Boolean
):java.io.Serializable