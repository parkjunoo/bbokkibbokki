package com.example.bbokkibbokki

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_adult.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.adult_start

class AdultActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adult)


        //오리지널 모드 화면으로 전환
        orriginal_start.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    //체크된 상태 취소 시 코드
                    val intent = Intent(this@AdultActivity, AdultActivity::class.java)
                    startActivity(intent)
                } else {
                    //체크된 상태로 만들 시 코드

                }
            }
        })

    }

}