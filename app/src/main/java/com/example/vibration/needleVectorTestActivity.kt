package com.example.vibration

import android.R.attr.angle
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.vibration.databinding.ActivityNeedleVectorTestBinding


class needleVectorTestActivity : AppCompatActivity(), SensorEventListener{
    private lateinit var binding: ActivityNeedleVectorTestBinding
    private lateinit var sensorManager: SensorManager
    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    private val mOrientationAngles = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    var degree = 0
    var dura = 100
    var ampli = 100
    val pi = 3.1415926
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType", "ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        binding = DataBindingUtil.setContentView(this, R.layout.activity_needle_vector_test)
        binding.drawRegion.setBackgroundResource(R.raw.halfstraight)
        binding.degreeButton.setOnClickListener {
            if(binding.degreeInput.text.toString().toInt() >= 90){
                degree = 90
            }else if(binding.degreeInput.text.toString().toInt() >= -90){
                degree = binding.degreeInput.text.toString().toInt()
            }else{
                degree = -90
            }
            binding.textView2.text = degree.toString()
            binding.drawRegion.rotation = degree.toFloat()
        }

        binding.drawRegion.setOnTouchListener(View.OnTouchListener { v, event ->
            //binding.textView.text = event.x.toString() + " " + binding.drawRegion.layoutParams.width.toString() + " " + event.y.toString() + " " + binding.drawRegion.layoutParams.height.toString()
            if (binding.switch2.isChecked && ampli < 200) {
                ampli = 200
            } else if (!binding.switch2.isChecked && ampli > 100) {
                ampli = 100
            }
            updateOrientationAngles()
            val vib: VibrationEffect = VibrationEffect.createOneShot(dura.toLong(), ampli)
            //binding.textView.text = "straight"
            if (event.x > binding.drawRegion.layoutParams.width / 3
                && event.x < binding.drawRegion.layoutParams.width * 2 / 3
                && event.y <= binding.drawRegion.layoutParams.height / 2
            ) {
                val vibrator = (getSystemService(VIBRATOR_SERVICE) as Vibrator).also {
                    it.vibrate(vib)
                }  //设置手机振动
                binding.textView2.text = "vibrating"
            } else {
                binding.textView2.text = "static"
            }
            false
        })
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
        // You must implement this callback in your code.
    }

    override fun onResume() {
        super.onResume()

        // Get updates from the accelerometer and magnetometer at a constant rate.
        // To make batch operations more efficient and reduce power consumption,
        // provide support for delaying updates to the application.
        //
        // In this example, the sensor reporting delay is small enough such that
        // the application receives an update before the system checks the sensor
        // readings again.
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
            sensorManager.registerListener(
                this,
                magneticField,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause() {
        super.onPause()

        // Don't receive any more updates from either sensor.
        sensorManager.unregisterListener(this)
    }

    // Get readings from accelerometer and magnetometer. To simplify calculations,
    // consider storing these readings as unit vectors.
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.size)
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
        }
        updateOrientationAngles()
    }

    // Compute the three orientation angles based on the most recent readings from
    // the device's accelerometer and magnetometer.
    @SuppressLint("SetTextI18n")
    fun updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )

        // "mRotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, mOrientationAngles)

        // "mOrientationAngles" now has up-to-date information.
        binding.angleText.text = (mOrientationAngles[0] * 180 / pi).toInt().toString() + " " +
                (mOrientationAngles[1] * 180 / pi).toInt().toString() + " " +
                (mOrientationAngles[2] * 180 / pi).toInt().toString()
        binding.drawRegion.rotation = degree - (mOrientationAngles[0] * 180 / pi).toFloat()
    }

}
