package com.example.bbokkibbokki.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbokkibbokki.R
import com.example.bbokkibbokki.model.PunishmentBox

class BoxlistAdapter(private val context : Context) : RecyclerView.Adapter<BoxlistAdapter.Holder>() {

    var mData = mutableListOf<PunishmentBox>();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxlistAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_punishment_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = mData.size;

    override fun onBindViewHolder(holder: BoxlistAdapter.Holder, position: Int) {
        holder.onBind(mData[position])
    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boxName : TextView = itemView.findViewById(R.id.box_name)
        val boxType : TextView = itemView.findViewById(R.id.box_name)
        fun onBind(punishmentBox : PunishmentBox){
            boxName.text = punishmentBox.boxName
            boxType.text = punishmentBox.type
        }
    }

}