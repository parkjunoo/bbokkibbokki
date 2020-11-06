package com.example.bbokkibbokki.activity

import com.example.bbokkibbokki.fragment.PunishmentList
import com.example.bbokkibbokki.fragment.General
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.amitshekhar.DebugDB
import com.example.bbokkibbokki.R
import com.example.bbokkibbokki.util.AppDataBase
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity:AppCompatActivity() {

    //Fragments
    private var generalFragment: General? = null
    private var gunishmentList: PunishmentList? = null
    // 토글 버튼
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var appDataBase: AppDataBase? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        generalFragment = General()
        gunishmentList = PunishmentList()

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.menu)

        mDrawerToggle =
            ActionBarDrawerToggle(this, hamburger, toolbar, R.string.open, R.string.close)
        mDrawerToggle!!.syncState()
        alarm_hamburger_startpage.setOnClickListener {
            hamburger!!.closeDrawer(GravityCompat.START)
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container, generalFragment!!).commit();

        tabs.addTab(tabs.newTab().setText("General"))
        tabs.addTab(tabs.newTab().setIcon(R.drawable.icon_unlock))

        tabs.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                var selected: Fragment? = null
                if (position == 0) selected = generalFragment
                else if (position == 1)  selected = gunishmentList
                supportFragmentManager.beginTransaction().replace(R.id.container, selected!!).commit()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        DebugDB.getAddressLog();
        appDataBase = AppDataBase.getInstance(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.appbar_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show()
            return true
        }
        return super.onContextItemSelected(item);
    }

}
