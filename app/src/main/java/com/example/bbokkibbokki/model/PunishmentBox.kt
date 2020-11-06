package com.example.bbokkibbokki.model

import androidx.room.*

@Entity(tableName = "punishment_box")
class PunishmentBox(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "box_seq") var boxSeq:Int?,
                         @ColumnInfo(name = "box_type") var type:String,
                         @ColumnInfo(name = "box_name") var boxName:String,
                         @ColumnInfo(name = "box_is_purchase") var isPurchase:Boolean
) {}
