package com.example.agendadecontantoscompose

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.agendadecontantoscompose.constants.Constants
import com.example.agendadecontantoscompose.dao.ContatoDao
import com.example.agendadecontantoscompose.model.Contato

@Database(entities = [Contato::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    Constants.DB_CONTATOS
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance

            }
        }
    }
}