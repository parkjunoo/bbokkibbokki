package com.example.bbokkibbokki;

public class SplashActivity : AppCompatActivity() {



        override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



        val intent = Intent(this, MainActivity::class.java)            // 실제 사용할 메인 액티비티

        startActivity(intent)

        finish()

        }

        }
