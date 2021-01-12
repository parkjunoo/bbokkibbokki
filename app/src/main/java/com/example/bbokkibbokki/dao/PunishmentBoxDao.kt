package com.example.bbokkibbokki.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.bbokkibbokki.model.PunishmentBox

@Dao
interface PunishmentBoxDao {
    @Query("SELECT * FROM punishment_box")
    fun getAll(): List<PunishmentBox>
    @Insert(onConflict = REPLACE)
    fun insert(punishmentBox: PunishmentBox)
    @Query("DELETE from punishment_box")
    fun deleteAll()
}