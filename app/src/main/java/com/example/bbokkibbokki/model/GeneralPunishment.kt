package com.example.bbokkibbokki.model

import androidx.room.Entity

// 일반 벌칙
@Entity()
class GeneralPunishment (punishmentNum:Int, quantity:Int, punishmentContent:String) {
    var punishmentNum:Int = 0 // 벌칙 넘버
    var quantity:Int = 0 // 해당벌칙의 수량
    var punishmentContent:String ="" // 해당 벌칙 내용
    init{
        this.punishmentNum = punishmentNum
        this.quantity = quantity
        this.punishmentContent = punishmentContent

    }
}