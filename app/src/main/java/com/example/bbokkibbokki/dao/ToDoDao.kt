package com.example.bbokkibbokki.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bbokkibbokki.model.ToDo


@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDo")
    fun getAll(): LiveData<List<ToDo>>
    @Insert
    fun insert(todo: ToDo)
    @Update
    fun update(todo: ToDo)
    @Delete
    fun delete(todo: ToDo)
    @Query("DELETE FROM ToDo")
    fun deleteAll()
}