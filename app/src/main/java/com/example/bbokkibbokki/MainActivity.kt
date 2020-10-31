package com.example.bbokkibbokki

import Fragment.Couple
import Fragment.General
import Model.GeneralPunishment
import Model.Stick
import android.R.id
import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity:AppCompatActivity(), SensorEventListener {

    //Fragments
    private var GeneralFragment: General? = null
    private var CoupleFragment: Couple? = null

    //애니메이션 변수
    private lateinit var box: ImageView
    private var sticks = ArrayList<Stick>()
    private var sticksTmp = ArrayList<ImageView>()
    private var randomNum: Int = 0
    private var purnishmentSum: Int = 0

    //shake 변수
    private var random = Random()
    private var lastTime: Long = 0
    private var speed: Float = 0.toFloat()
    private var lastX: Float = 0.toFloat()
    private var lastY: Float = 0.toFloat()
    private var lastZ: Float = 0.toFloat()
    private var x: Float = 0.toFloat()
    private var y: Float = 0.toFloat()
    private var z: Float = 0.toFloat()
    private var SHAKE_THRESHOLD = 800
    private var DATA_X = SensorManager.DATA_X
    private var DATA_Y = SensorManager.DATA_Y
    private var DATA_Z = SensorManager.DATA_Z
    private var sensorManager: SensorManager? = null
    private var accelerormeterSensor: Sensor? = null
    private var G_PunishmentList: ArrayList<GeneralPunishment>? = ArrayList<GeneralPunishment>()

    // 토글 버튼
    private var mDrawerToggle: ActionBarDrawerToggle? = null




    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        GeneralFragment = General()
        CoupleFragment = Couple()

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.menu)

        mDrawerToggle =
            ActionBarDrawerToggle(this, hamburger, toolbar, R.string.open, R.string.close)
        mDrawerToggle!!.syncState()
        alarm_hamburger_startpage.setOnClickListener {
            hamburger!!.closeDrawer(GravityCompat.START)
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container, GeneralFragment!!).commit();

        tabs.addTab(tabs.newTab().setText("General"))
        tabs.addTab(tabs.newTab().setText("Couple"))

        tabs.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                var selected: Fragment? = null
                if (position == 0) selected = GeneralFragment
                else if (position == 1) selected = CoupleFragment
                supportFragmentManager.beginTransaction().replace(R.id.container, selected!!).commit()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

//            sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
//            accelerormeterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//            for(j in G_PunishmentList!!){
//                purnishmentSum = purnishmentSum + j.quantity
//            }
//            count.setText("${purnishmentSum}")
//
//
//        //스틱 객체 생성 후 추가 (관리의 용의)
//        sticks.add(Stick(stick1))
//        sticks.add(Stick(stick2))
//        sticks.add(Stick(stick3))
//        sticks.add(Stick(stick4))
//        sticks.add(Stick(stick5))
//
//        sticksTmp.add(stick1) // 애니메이션을 위한 임시 stick List
//        sticksTmp.add(stick2)
//        sticksTmp.add(stick3)
//        sticksTmp.add(stick4)
//        sticksTmp.add(stick5)
//
//
//
//        //스틱 OnTouch
//        for (i in sticks) {
//
//            i.ImageView.setOnTouchListener { v, e ->
//                //부모의 절대좌표
//                val pWidth = (v.parent as ViewGroup).width
//                val pHeight = (v.parent as ViewGroup).height
//
//                //x움직일때
//                //v.x   v.y    가상의 수직교점 절대좌표
//                //e.x   e.y    터치한 지점에 해당하는 절대좌표
//                if (e.action == MotionEvent.ACTION_MOVE) {
//                    //v.x = v.x + e.x - v.width / 2
//                    if (v.y >= v.y + e.y - v.height / 2) {
//                        v.y = v.y + e.y - v.height / 2
//                    }
//                    //뗐을 때
//                } else if (e.action == MotionEvent.ACTION_UP) {
//
//                    Log.d("bsjbsj", "detached...")
//                    Log.d(
//                        "bsjbsj",
//                        "v.x : ${v.x} + v.y : ${v.y} , v.x + v.width : ${v.x + v.width}, v.y + y.width : ${v.y + v.width}"
//                    )
//                    // 뽑았을때  v.x : 121.23216 + v.y : 92.1308 , v.x + v.width : 321.23218, v.y + y.width : 292.1308
//                    // 아래로 v.x : 663.0 + v.y : 572.792 , v.x + v.width : 903.0, v.y + y.width : 812.792       --->    570.0<v.y
////                    if (v.x < 0) {
////                        v.x = 0F
////                    } else if (v.x + v.width > pWidth) {
////                        v.x = (pWidth - v.width).toFloat()
////                    }
//
//                    if (v.y < 0) {
//                        v.y = 0F
//                        //점점 사라지게
//                        i.ImageView.animate().alpha(0f).setDuration(2000).withEndAction {
//                            i.ImageView.alpha = 1f
//                            v.y = sticksTmp.get(0).y
//                        }.start()
//                        i.ImageView.setImageResource(0);
//
//
//                        //벌칙 줄이기 (총 2번씩)  -> 0일때 다시 초기화
//                        G_PunishmentList!!.get(randomNum).quantity--
//                        if(G_PunishmentList?.get(randomNum)!!.quantity == 0){
//                            if(G_PunishmentList?.size != 0){
//
//                                G_PunishmentList?.removeAt(randomNum)
//                                count.setText("${--purnishmentSum}")
//                            }else{
////                                val nullDialog = AlertDialog.Builder(this@MainActivity)
////                                nullDialog.setTitle("벌칙이 모두 사라졌어요!!")
////                                nullDialog.setPositiveButton("닫기"){dialog, id ->}
////                                nullDialog.show()
//                            }
//                            //추후 벌칙 뽑은후 횟수 조정
//                        }
//                        randomNum = rand(0,G_PunishmentList!!.size)
//
//                        //벌칙창
//                        val mAlertDialog = AlertDialog.Builder(this@MainActivity)
//                        mAlertDialog.setIcon(R.drawable.soju)
//                        mAlertDialog.setTitle("*^ㅅ^*")
//                        mAlertDialog.setMessage(G_PunishmentList!!.get(randomNum).punishmentContent)
//                        mAlertDialog.setPositiveButton("닫기"){dialog, id ->
//                            //stick 제자리
//                        }
//                        mAlertDialog.show()
//                        v.y = sticksTmp.get(0).y
//
//                    } else if (v.y + v.height > pHeight) {
//                        v.y = (pHeight - v.height).toFloat()
//                    }
//
//                    v.y = sticksTmp.get(0).y
//
//                }
//                true
//            }
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

    //리스트 길이 만큼 랜덤한 숫자 출력
    fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }


    override fun onStart() {
        super.onStart()
        if (accelerormeterSensor != null) {
            sensorManager?.registerListener(
                this,
                accelerormeterSensor,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }


    // 깃 테스트용 수정
    override fun onStop() {
        super.onStop()
        if (sensorManager != null)
            sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {

//
    }


    //흔들기 애니메이션
    private fun animateTotal() {
        sticksTmp.shuffle()
        for (i in 0..sticks.size - 1) {
            sticks.get(i).ImageView.animate()
                .translationXBy(sticksTmp.get(i).x - sticks.get(i).ImageView.x).setDuration(200L)
                .start()
        }
        val shake = AnimationUtils.loadAnimation(this, R.anim.activity_shake)
        box.startAnimation(shake)
    }


    //결제 물어보기
//    private fun checkPayment(){
//        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = inflater.inflate(R.layout.check_payment, null)
//        val textView: TextView = view.findViewById(R.id.payment)
//        textView.text = "테스트 테스트 결제하시겠습니까?"
//
//
//        val alertDialog = AlertDialog.Builder(this)
//            .setTitle("결제창")
//            .setPositiveButton("결제"){dialog, which ->
//                //test
//                Toast.makeText(applicationContext, "test 결제완료",Toast.LENGTH_SHORT
//                )
//                //결제화면으로 넘어가야함
//
//                //adult로 넘어가는 화면 구현 intent 합치기
//
//            }
//            .setNeutralButton("취소", null)
//            .create()
//    }


}
