package com.example.bbokkibbokki

import Model.GeneralPunishment
import Model.Stick
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.os.Vibrator
import android.util.Log

import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import java.util.*
import kotlin.collections.ArrayList

class MainActivity:AppCompatActivity(), SensorEventListener {

    //애니메이션 변수
    private lateinit var box: ImageView
    private var sticks = ArrayList<Stick>()
    private var sticksTmp = ArrayList<ImageView>()
    private var randomNum: Int = 0
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
    private var G_PunishmentList = ArrayList<GeneralPunishment>()


    init {
        // 일반벌칙 정의
        G_PunishmentList.add(GeneralPunishment(1, 2, "벌칙1"))
        G_PunishmentList.add(GeneralPunishment(2, 2, "벌칙2"))
        G_PunishmentList.add(GeneralPunishment(3, 2, "벌칙3"))
        G_PunishmentList.add(GeneralPunishment(4, 2, "벌칙4"))
        G_PunishmentList.add(GeneralPunishment(5, 2, "벌칙5"))

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
        if (event.sensor.getType() === Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()
            val gabOfTime = (currentTime - lastTime)
            //진동 설정정
            var vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (gabOfTime > 100) {
                lastTime = currentTime
                x = event.values[SensorManager.DATA_X]
                y = event.values[SensorManager.DATA_Y]
                z = event.values[SensorManager.DATA_Z]
                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000
                //흔들때
                if (speed > SHAKE_THRESHOLD) {
                    //흔들기 애니메이션

                    box = findViewById(R.id.Box)
                    animateTotal()

                    //흔들시 효과음
                    val soundPool = SoundPool.Builder().build()
                    val soundId = soundPool.load(this, R.raw.swing, 1)
                    soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f)
                    //0.5초간 진동
                    vibrator.vibrate(500)
                    Thread.sleep(230)


                    // 랜덤 변수 생성
                    randomNum = rand(0, G_PunishmentList.size)
                    if (G_PunishmentList.get(randomNum).quantity == 0) {

                        //추후 벌칙 뽑은후 횟수 조정
                    }


                }
                lastX = event.values[DATA_X]
                lastY = event.values[DATA_Y]
                lastZ = event.values[DATA_Z]
            }
        }
    }


    //흔들기 애니메이션
    private fun animateTotal() {
        sticksTmp.shuffle()
        for(i in 0..sticks.size-1){
            sticks.get(i).ImageView.animate().translationXBy(sticksTmp.get(i).x  - sticks.get(i).ImageView.x).setDuration(200L).start()
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerormeterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


//        //결제 여쭈어보기 alertDialog
//        adult_start.setOnClickListener{
//            checkPayment()
//    }
        //성인버튼눌렀을때 화면전환
        val go_adult = findViewById(R.id.adult_start) as Button
        go_adult.setOnClickListener {
            val intent = Intent(this@MainActivity, AdultActivity::class.java)
            startActivity(intent)
        }
        //스틱 객체 생성 후 추가 (관리의 용의)
        sticks.add(Stick(stick1))
        sticks.add(Stick(stick2))
        sticks.add(Stick(stick3))
        sticks.add(Stick(stick4))
        sticks.add(Stick(stick5))

        sticksTmp.add(stick1) // 애니메이션을 위한 임시 stick List
        sticksTmp.add(stick2)
        sticksTmp.add(stick3)
        sticksTmp.add(stick4)
        sticksTmp.add(stick5)

        //스틱 OnTouch
        for (i in sticks) {
            i.ImageView.setOnTouchListener { v, e ->
                //부모의 절대좌표
                val pWidth = (v.parent as ViewGroup).width
                val pHeight = (v.parent as ViewGroup).height

                //x움직일때
//                //v.x   v.y    가상의 수직교점 절대좌표
//                //e.x   e.y    터치한 지점에 해당하는 절대좌표
                if (e.action == MotionEvent.ACTION_MOVE) {
//                    v.x = v.x + e.x - v.width / 2
                    v.y = v.y + e.y - v.height / 2

                    //뗐을 때
                } else if (e.action == MotionEvent.ACTION_UP) {
                    Log.d("bsjbsj", "detached...")
                    Log.d(
                        "bsjbsj",
                        "v.x : ${v.x} + v.y : ${v.y} , v.x + v.width : ${v.x + v.width}, v.y + y.width : ${v.y + v.width}"
                    )
                    // 뽑았을때  v.x : 121.23216 + v.y : 92.1308 , v.x + v.width : 321.23218, v.y + y.width : 292.1308
                    // 아래로 v.x : 663.0 + v.y : 572.792 , v.x + v.width : 903.0, v.y + y.width : 812.792       --->    570.0<v.y
//                    if (v.x < 0) {
//                        v.x = 0F
//                    } else if (v.x + v.width > pWidth) {
//                        v.x = (pWidth - v.width).toFloat()
//                    }

                    if (v.y < 0) {
                        v.y = 0F
                        //점점 사라지게
                        i.ImageView.animate().alpha(0f).setDuration(2000).withEndAction {
                            i.ImageView.alpha = 1f
                        }.start()
                        i.ImageView.setImageResource(0);

                        //벌칙 줄이기 (총 2번씩)  -> 0일때 다시 초기화
                        G_PunishmentList.get(randomNum).quantity--
                        if(G_PunishmentList.get(randomNum).quantity == 0){
                            G_PunishmentList.removeAt(randomNum)
                            //추후 벌칙 뽑은후 횟수 조정
                        }
                        randomNum = rand(0,G_PunishmentList.size)

                        //벌칙창
                        val mAlertDialog = AlertDialog.Builder(this@MainActivity)
                        mAlertDialog.setIcon(R.drawable.soju)
                        mAlertDialog.setTitle("*^ㅅ^*")
                        mAlertDialog.setMessage(G_PunishmentList.get(randomNum).punishmentContent)
                        mAlertDialog.setPositiveButton("닫기"){dialog, id ->
                            //stick 제자리
                            v.y = 560F
                        }
                        mAlertDialog.show()



                    } else if (v.y + v.height > pHeight) {
                        v.y = (pHeight - v.height).toFloat()
                    }

                }
                true
            }
        }

    }
}
