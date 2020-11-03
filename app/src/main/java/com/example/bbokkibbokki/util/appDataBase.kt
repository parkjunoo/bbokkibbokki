package com.example.bbokkibbokki.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bbokkibbokki.model.Punishment

@Database(entities = [Punishment::class], version = 1)
abstract class appDataBase: RoomDatabase() {
    abstract fun catDao(): Punishment
    companion object {
        private var INSTANCE: appDataBase? = null

        fun getInstance(context: Context): appDataBase? {
            if (INSTANCE == null) {
                synchronized(appDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        appDataBase::class.java, "bbokkibbokki.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}