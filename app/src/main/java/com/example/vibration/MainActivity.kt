package com.example.vibration

import android.annotation.SuppressLint
import android.app.Service
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.vibration.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var ampli = 1
    var dura = 1
    var direction = 2

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.buttonUp.setOnClickListener {
            vibrate(it, 1)
        }
        binding.buttonDown.setOnClickListener {
            vibrate(it, 2)
        }
        binding.buttonLeft.setOnClickListener {
            vibrate(it, 3)
        }
        binding.buttonRight.setOnClickListener {
            vibrate(it, 4)
        }
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun vibrate(view: View, selectDirection: Int) {
        var text = "Failed"
        var selectText = "Top"
        var expectText = "Top"
        if(selectDirection == direction){
            dura = 500
            ampli = 100
            text = "SUCCESS"
        }else{
            dura = 50
            ampli = 200
        }
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
        selectText = when (selectDirection) {
            1 -> {
                "Top"
            }
            2 -> {
                "Down"
            }
            3 -> {
                "Left"
            }
            else
            -> {
                "Right"
            }
        }

        expectText = when (direction) {
            1 -> {
                "Top"
            }
            2 -> {
                "Down"
            }
            3 -> {
                "Left"
            }
            else
            -> {
                "Right"
            }
        }
        val vib: VibrationEffect = VibrationEffect.createOneShot(dura.toLong(), ampli)
        val vibrator = (getSystemService(Service.VIBRATOR_SERVICE) as Vibrator).also {
            it.vibrate(vib)
        };  //设置手机振动
        binding.textView.text = "Expect: $expectText Selected: $selectText"
        direction = Random().nextInt(4) + 1
        val drawableResource = when (direction) {
            1 -> R.raw.up
            2 -> R.raw.down
            3 -> R.raw.left
            else -> R.raw.right
        }
        binding.imageView3.setImageResource(drawableResource)
    }
}