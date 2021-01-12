package com.example.bbokkibbokki.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbokkibbokki.R
import com.example.bbokkibbokki.model.PunishmentBox

class BoxlistAdapter(private var mData: MutableList<PunishmentBox>) : RecyclerView.Adapter<BoxlistAdapter.Holder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxlistAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.punishment_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = mData.size;

    override fun onBindViewHolder(holder: BoxlistAdapter.Holder, position: Int) {
        holder.onBind(mData[position])
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boxName : TextView = itemView.findViewById(R.id.box_name)
        val boxType : TextView = itemView.findViewById(R.id.box_name)
        fun onBind(punishmentBox : PunishmentBox){
            boxName.text = punishmentBox.boxName
            boxType.text = punishmentBox.type
        }
    }

}