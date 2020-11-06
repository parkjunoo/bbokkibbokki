package com.example.bbokkibbokki.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.bbokkibbokki.model.Punishment

    @Dao
    interface PunishmentDao {
        @Query("SELECT * FROM punishment")
        fun getAll(): List<Punishment>

        @Insert(onConflict = REPLACE)
        fun insert(punishment: Punishment)

        @Query("DELETE FROM punishment")
    fun deleteAll()
}