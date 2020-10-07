package com.example.bbokkibbokki

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdultActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adult)


        //오리지널 모드 화면으로 전환
        val go_adult = findViewById(R.id.orriginal_start) as Button
        go_adult.setOnClickListener{
            val intent = Intent(this@AdultActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

}