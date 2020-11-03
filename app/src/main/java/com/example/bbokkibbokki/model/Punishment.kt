package com.example.bbokkibbokki.model

import androidx.room.*

@Entity(
    tableName = "punishment",
    indices = [Index("box_seq")],
    foreignKeys = [ForeignKey(entity = PunishmentBox::class, parentColumns = ["parent_seq"], childColumns = ["box_seq"], onDelete = ForeignKey.NO_ACTION)]
)
class Punishment(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "punishment_seq") var punishmentId:Int,
                 @ColumnInfo(name = "box_seq") var boxSet:Int,
                 @ColumnInfo(name = "quantity") var quantity:Int,
                 @ColumnInfo(name = "content") var punishmentContent:String){
}
