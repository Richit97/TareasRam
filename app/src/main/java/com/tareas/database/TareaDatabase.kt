package com.tareas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tareas.models.Tarea
import com.tareas.utilities.DATABASE_NAME

@Database(entities = [Tarea::class], version = 1, exportSchema = false)
abstract class TareaDatabase: RoomDatabase() {

    abstract fun getTareaDao() : TareaDao companion object {

        @Volatile
        private var INSTANCE : TareaDatabase? = null

        fun getDatabase(context: Context) : TareaDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TareaDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}