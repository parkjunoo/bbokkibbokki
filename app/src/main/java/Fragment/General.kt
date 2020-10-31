package Fragment

import Model.GeneralPunishment
import Model.Stick
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.bbokkibbokki.R
import kotlinx.android.synthetic.main.fragment_couple.view.*
import kotlinx.android.synthetic.main.fragment_general.*
import java.util.*
import kotlin.collections.ArrayList

class General : Fragment(), SensorEventListener{

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

    init {
        // 일반벌칙 정의
        G_PunishmentList!!.add(GeneralPunishment(1, 1, "벌주 2배로 마시기!!"))
        G_PunishmentList!!.add(GeneralPunishment(2, 1, "노래부르기 (1절매너)"))
        G_PunishmentList!!.add(GeneralPunishment(3, 1, "소주 한 잔 숟가락으로 먹기"))
        G_PunishmentList!!.add(GeneralPunishment(4, 1, "자리에서 절하고 마시기"))
        G_PunishmentList!!.add(GeneralPunishment(5, 1, "흑기사,흑장미 무료이용찬스!"))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        sensorManager = activity!!.getSystemService(SENSOR_SERVICE) as SensorManager
        accelerormeterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        for(j in G_PunishmentList!!){
            purnishmentSum = purnishmentSum + j.quantity
        }

        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_general, container, false)
        //스틱 객체 생성 후 추가 (관리의 용의)
        sticks.add(Stick(view.stick1))
        sticks.add(Stick(view.stick2))
        sticks.add(Stick(view.stick3))
        sticks.add(Stick(view.stick4))
        sticks.add(Stick(view.stick5))

        sticksTmp.add(view.stick1) // 애니메이션을 위한 임시 stick List
        sticksTmp.add(view.stick2)
        sticksTmp.add(view.stick3)
        sticksTmp.add(view.stick4)
        sticksTmp.add(view.stick5)



        //스틱 OnTouch
        for (i in sticks) {
            i.ImageView.setOnTouchListener { v, e ->
                //부모의 절대좌표
                val pWidth = (v.parent as ViewGroup).width
                val pHeight = (v.parent as ViewGroup).height

                //x움직일때
                //v.x   v.y    가상의 수직교점 절대좌표
                //e.x   e.y    터치한 지점에 해당하는 절대좌표
                if (e.action == MotionEvent.ACTION_MOVE) {
                    //v.x = v.x + e.x - v.width / 2
                    if (v.y >= v.y + e.y - v.height / 2) {
                        v.y = v.y + e.y - v.height / 2
                    }
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
                            v.y = sticksTmp.get(0).y
                        }.start()
                        i.ImageView.setImageResource(0);


                        //벌칙 줄이기 (총 2번씩)  -> 0일때 다시 초기화
                        G_PunishmentList!!.get(randomNum).quantity--
                        if (G_PunishmentList?.get(randomNum)!!.quantity == 0) {
                            if (G_PunishmentList?.size != 0) {

                                G_PunishmentList?.removeAt(randomNum)
                                count.setText("${--purnishmentSum}")
                            } else {
//                                val nullDialog = AlertDialog.Builder(this@MainActivity)
//                                nullDialog.setTitle("벌칙이 모두 사라졌어요!!")
//                                nullDialog.setPositiveButton("닫기"){dialog, id ->}
//                                nullDialog.show()
                            }
                            //추후 벌칙 뽑은후 횟수 조정
                        }
                        randomNum = rand(0, G_PunishmentList!!.size)

                        //벌칙창
                        val mAlertDialog = AlertDialog.Builder(this.activity)
                        mAlertDialog.setIcon(R.drawable.soju)
                        mAlertDialog.setTitle("*^ㅅ^*")
                        mAlertDialog.setMessage(G_PunishmentList!!.get(randomNum).punishmentContent)
                        mAlertDialog.setPositiveButton("닫기") { dialog, id ->
                            //stick 제자리
                        }
                        mAlertDialog.show()
                        v.y = sticksTmp.get(0).y

                    } else if (v.y + v.height > pHeight) {
                        v.y = (pHeight - v.height).toFloat()
                    }

                    v.y = sticksTmp.get(0).y

                }
                true
            }
        }
        return view

    }
    fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }
    private fun animateTotal() {
        sticksTmp.shuffle()
        for (i in 0..sticks.size - 1) {
            sticks.get(i).ImageView.animate()
                .translationXBy(sticksTmp.get(i).x - sticks.get(i).ImageView.x).setDuration(200L)
                .start()
        }
        val shake = AnimationUtils.loadAnimation(this.activity, R.anim.activity_shake)
        box.startAnimation(shake)
    }

    @SuppressLint("ServiceCast")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.getType() === Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()
            val gabOfTime = (currentTime - lastTime)
            //진동 설정정
            var vibrator = this.activity!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (gabOfTime > 100) {
                lastTime = currentTime
                x = event!!.values[SensorManager.DATA_X]
                y = event!!.values[SensorManager.DATA_Y]
                z = event!!.values[SensorManager.DATA_Z]
                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000
                //흔들때
                if (speed > SHAKE_THRESHOLD) {
                    println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                    //흔들기 애니메이션
                    animateTotal()
                    //흔들시 효과음
//                    val soundPool = SoundPool.Builder().build()
//                    val soundId = soundPool.load(this, R.raw.swing, 1)
//                    soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f)
//                    //0.5초간 진동
                    vibrator.vibrate(500)
                    Thread.sleep(230)

                    // 랜덤 변수 생성
                    randomNum = rand(0, G_PunishmentList!!.size)
                    if (G_PunishmentList!!.get(randomNum).quantity == 0) {

                        //추후 벌칙 뽑은후 횟수 조정
                    }

                }
                lastX = event.values[DATA_X]
                lastY = event.values[DATA_Y]
                lastZ = event.values[DATA_Z]
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}