package com.example.bbokkibbokki

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity:AppCompatActivity(), SensorEventListener {

    private var cnt:Int = 0
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
                    cnt++
                    action.text = "$cnt"
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