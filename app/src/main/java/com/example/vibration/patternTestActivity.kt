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
import com.example.vibration.databinding.ActivityPatternTestBinding
import java.util.*

class patternTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPatternTestBinding
    var ampli = 100
    var dura = 100
    var direction = 0

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pattern_test)
        binding.drawRegion.setBackgroundResource(R.raw.straight)
        binding.drawRegion.setOnTouchListener(View.OnTouchListener { v, event ->
            //binding.textView.text = event.x.toString() + " " + binding.drawRegion.layoutParams.width.toString() + " " + event.y.toString() + " " + binding.drawRegion.layoutParams.height.toString()
            binding.textView.text = ampli.toString()
            if(binding.switch1.isChecked && ampli < 200){
                ampli = 200
            } else if(!binding.switch1.isChecked && ampli > 100){
                ampli = 100
            }
            val vib: VibrationEffect = VibrationEffect.createOneShot(dura.toLong(), ampli)
            when(direction) {
                0 -> {
                    //binding.textView.text = "straight"
                    if (event.x > binding.drawRegion.layoutParams.width / 3 && event.x < binding.drawRegion.layoutParams.width * 2 / 3){
                        val vibrator = (getSystemService(VIBRATOR_SERVICE) as Vibrator).also {
                            it.vibrate(vib)
                        };  //设置手机振动
                        binding.textView.text = "vibrating"
                    }else{
                        binding.textView.text = "static"
                    }
                }
                1 -> {
                    if (((event.x > binding.drawRegion.layoutParams.width / 3 && event.x < binding.drawRegion.layoutParams.width * 2 / 3)
                                && event.y > binding.drawRegion.layoutParams.height / 3)
                        || ((event.y < binding.drawRegion.layoutParams.height / 3)
                                &&(event.x < binding.drawRegion.layoutParams.width * 2 / 3))){
                        val vibrator = (getSystemService(VIBRATOR_SERVICE) as Vibrator).also {
                            it.vibrate(vib)
                        };  //设置手机振动
                        binding.textView.text = "vibrating"
                    }else{
                        binding.textView.text = "static"
                    }
                }
                else -> {
                    //binding.textView.text = "right"
                    if (((event.x > binding.drawRegion.layoutParams.width / 3 && event.x < binding.drawRegion.layoutParams.width * 2 / 3)
                                && event.y > binding.drawRegion.layoutParams.height / 3)
                        || ((event.y < binding.drawRegion.layoutParams.height / 3)
                                &&(event.x > binding.drawRegion.layoutParams.width / 3))){
                        val vibrator = (getSystemService(VIBRATOR_SERVICE) as Vibrator).also {
                            it.vibrate(vib)
                        }  //设置手机振动
                        binding.textView.text = "vibrating"
                    }else{
                        binding.textView.text = "static"
                    }
                }
            }
            false
        })
        binding.straightButton.setOnClickListener {
            changeRoute(it, 0)
        }
        binding.leftButton.setOnClickListener {
            changeRoute(it, 1)
        }
        binding.rightButton.setOnClickListener {
            changeRoute(it, 2)
        }
    }

    private fun changeRoute(view: View, selectDirection: Int){
        direction = selectDirection
        val drawableResource = when (selectDirection) {
            0 -> R.raw.straight
            1 -> R.raw.left
            else -> R.raw.right
        }
        binding.imageView3.setImageResource(drawableResource)
        binding.drawRegion.setBackgroundResource(drawableResource)
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
