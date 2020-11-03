package com.example.bbokkibbokki.model

import androidx.room.*

@Entity(

)
class PunishmentBox(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "box_seq") var boxSeq:Int,
                    @ColumnInfo(name = "box_type")var type:Int,
                    @ColumnInfo(name = "box_name")var boxName:String ) {}