package com.example.bbokkibbokki.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.bbokkibbokki.model.Punishment

@Dao
interface PunishmentBoxDao {
    @Query("SELECT * FROM Punishment")
    fun getAll(): List<Punishment>

    @Insert(onConflict = REPLACE)
    fun insert(punishment: Punishment)

    @Query("DELETE from Punishment")
    fun deleteAll()
}