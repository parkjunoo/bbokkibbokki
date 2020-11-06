package com.example.bbokkibbokki.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ToDo(@PrimaryKey(autoGenerate = true) var id: Int, var name:String)
{}