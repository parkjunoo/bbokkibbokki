package com.example.bbokkibbokki.model

import androidx.room.*

@Entity(tableName = "punishment")
class Punishment(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "punish_seq") var punishmentId:Int?,
                      @ColumnInfo(name = "box_seq") var boxSet:Int,
                      @ColumnInfo(name = "punish_quantity") var quantity:Int,
                      @ColumnInfo(name = "punish_content") var punishmentContent:String
){}
