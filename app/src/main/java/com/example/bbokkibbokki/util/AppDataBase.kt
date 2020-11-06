package com.example.bbokkibbokki.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bbokkibbokki.dao.PunishmentBoxDao
import com.example.bbokkibbokki.dao.PunishmentDao
import com.example.bbokkibbokki.dao.ToDoDao
import com.example.bbokkibbokki.model.Punishment
import com.example.bbokkibbokki.model.PunishmentBox
import com.example.bbokkibbokki.model.ToDo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ToDo::class, PunishmentBox::class, Punishment::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun punishmentDao(): PunishmentDao
    abstract fun punishmentBoxDao(): PunishmentBoxDao
    abstract fun todoDao(): ToDoDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java, "bbokkibbokki.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        addPreDatabase(database.punishmentBoxDao())
                    }
                }
            }
            suspend fun addPreDatabase(punishmentBoxDao: PunishmentBoxDao) {

                //userDao.deleteAll()
                // Add User
                //punishmentBoxDao.insert(UserEntity("Lilly","여","1993-07-25"))
            }
        }
    }
}