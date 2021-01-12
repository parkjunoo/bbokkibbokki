package com.example.bbokkibbokki.fragment

import android.os.Bundle
import android.os.RemoteCallbackList
import android.util.Log
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_punishment_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mDatas = mutableListOf<PunishmentBox>()
        mDatas.add(PunishmentBox(1,"222","333",true))
        mDatas.add(PunishmentBox(2,"222","333",true))
        mDatas.add(PunishmentBox(3,"222","333",true))

        // Fragment에서 전달받은 list를 넘기면서 ListAdapter 생성z
        boxRecyclerView = view.findViewById(R.id.punishment_box_list);
        mAdapter = BoxlistAdapter(mDatas)
        boxRecyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        // RecyclerView.adapter에 지정
        boxRecyclerView.adapter = mAdapter
    }
}