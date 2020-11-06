package com.example.bbokkibbokki.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.bbokkibbokki.R
import com.example.bbokkibbokki.model.ToDo
import com.example.bbokkibbokki.util.AppDataBase
import com.example.bbokkibbokki.util.InsertAsyncTask
import kotlinx.android.synthetic.main.fragment_punishment_list.view.*

class PunishmentList : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_punishment_list, container, false)
        val plantObserver = Observer<List<ToDo>> {
                newList ->
            view.textView.text = newList.toString();
        }
        var DataBase : AppDataBase? = AppDataBase.getInstance(this.context!!)
        DataBase!!.todoDao().getAll().observe(this, plantObserver)
        view.textView.setText(DataBase.todoDao().getAll().toString())
        view.btn.setOnClickListener(){
            InsertAsyncTask(DataBase.todoDao()).execute(ToDo(0,view.editText.text.toString()))
        }
        return view
    }
}