package com.example.bbokkibbokki.fragment

import android.os.Bundle
import android.os.RemoteCallbackList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bbokkibbokki.R
import com.example.bbokkibbokki.adapter.BoxlistAdapter
import com.example.bbokkibbokki.model.PunishmentBox
import com.example.bbokkibbokki.model.ToDo
import com.example.bbokkibbokki.util.AppDataBase
import com.example.bbokkibbokki.util.InsertAsyncTask
import kotlinx.android.synthetic.main.fragment_punishment_list.*
import kotlinx.android.synthetic.main.fragment_punishment_list.view.*

class PunishmentList : Fragment() {
    private lateinit var boxRecyclerView: RecyclerView
    private lateinit var mAdapter : BoxlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_punishment_list, container, false)
        val mDatas = mutableListOf<PunishmentBox>()

        mDatas.add(PunishmentBox(1,"222","333",true))
        mDatas.add(PunishmentBox(2,"222","333",true))
        mDatas.add(PunishmentBox(3,"222","333",true))

        punishment_box_list.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            mAdapter = ListAdapter(mDatas)
        }

        boxRecyclerView = view.findViewById(R.id.punishment_box_list)
        boxRecyclerView.setLayoutManager(LinearLayoutManager(getActivity()))

        mAdapter = BoxlistAdapter(view.context)
        mAdapter.mData = mDatas


        mAdapter.notifyDataSetChanged()
        boxRecyclerView.adapter = mAdapter
        return view
    }
}