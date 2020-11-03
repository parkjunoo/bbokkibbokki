package com.example.bbokkibbokki.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bbokkibbokki.R
import kotlinx.android.synthetic.main.fragment_couple.view.*

class Couple : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_couple, container, false)
        view.textView.setText("1")
        return view
    }

}