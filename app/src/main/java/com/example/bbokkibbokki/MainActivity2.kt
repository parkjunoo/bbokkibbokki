package com.example.bbokkibbokki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bbokkibbokki.adapter.ViewPagerAdapter
import com.example.bbokkibbokki.fragment.AdultFragment
import com.example.bbokkibbokki.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        setUpTabs()
    }

    private  fun setUpTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(AdultFragment(), "Adult")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_home_24)
        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_insert_emoticon_24)

    }

}