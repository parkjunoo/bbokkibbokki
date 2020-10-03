package com.example.bbokkibbokki

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Random
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity:AppCompatActivity(), SensorEventListener {

    private var cnt:Int = 0
    private var random = Random()
    private var lastTime:Long = 0
    private var speed:Float = 0.toFloat()
    private var lastX:Float = 0.toFloat()
    private var lastY:Float = 0.toFloat()
    private var lastZ:Float = 0.toFloat()
    private var x:Float = 0.toFloat()
    private var y:Float = 0.toFloat()
    private var z:Float = 0.toFloat()
    private var SHAKE_THRESHOLD = 800
    private var DATA_X = SensorManager.DATA_X
    private var DATA_Y = SensorManager.DATA_Y
    private var DATA_Z = SensorManager.DATA_Z
    private var sensorManager:SensorManager? = null
    private var accelerormeterSensor:Sensor? = null
    private var G_PunishmentList = ArrayList<GeneralPunishment>()


    init{
        // 일반벌칙 정의
        G_PunishmentList.add(GeneralPunishment(1,2,"벌칙1"))
        G_PunishmentList.add(GeneralPunishment(2,2,"벌칙2"))
        G_PunishmentList.add(GeneralPunishment(3,2,"벌칙3"))
        G_PunishmentList.add(GeneralPunishment(4,2,"벌칙4"))
        G_PunishmentList.add(GeneralPunishment(5,2,"벌칙5"))
    }

    //리스트 길이 만큼 랜덤한 숫자 출력
    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }


    override fun onStart() {
        super.onStart()
        if (accelerormeterSensor != null)
            sensorManager?.registerListener(this, accelerormeterSensor, SensorManager.SENSOR_DELAY_GAME)
    }
    override fun onStop() {
        super.onStop()
        if (sensorManager != null)
            sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event:SensorEvent) {
        if (event.sensor.getType() === Sensor.TYPE_ACCELEROMETER)
        {
            val currentTime = System.currentTimeMillis()
            val gabOfTime = (currentTime - lastTime)
            if (gabOfTime > 100)
            {
                lastTime = currentTime
                x = event.values[SensorManager.DATA_X]
                y = event.values[SensorManager.DATA_Y]
                z = event.values[SensorManager.DATA_Z]
                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000
                if (speed > SHAKE_THRESHOLD)
                {
                    // 흔들때 이벤트 처리는 여기서 하면 되요 나영씨^^


                    // 랜덤 변수 생성
                    var randomNum = rand(0,G_PunishmentList.size)
                    if(G_PunishmentList.get(randomNum).quantity == 0){
                        //추후 벌칙 뽑은후 횟수 조정
                    }
                    action.text = "${G_PunishmentList.get(randomNum).punishmentContent}"

                }
                lastX = event.values[DATA_X]
                lastY = event.values[DATA_Y]
                lastZ = event.values[DATA_Z]
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerormeterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)



    }
}