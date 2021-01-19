package com.example.vibration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.vibration.databinding.ActivityMainBinding
import com.example.vibration.databinding.ActivityNeedleTestBinding

class needleTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNeedleTestBinding
    var degree = 0
    var dura = 100
    var ampli = 100
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_needle_test)
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
            if(binding.switch2.isChecked && ampli < 200){
                ampli = 200
            } else if(!binding.switch2.isChecked && ampli > 100){
                ampli = 100
            }
            val vib: VibrationEffect = VibrationEffect.createOneShot(dura.toLong(), ampli)
                    //binding.textView.text = "straight"
                    if (event.x > binding.drawRegion.layoutParams.width / 3
                        && event.x < binding.drawRegion.layoutParams.width * 2 / 3
                        && event.y <= binding.drawRegion.layoutParams.height / 2){
                        val vibrator = (getSystemService(VIBRATOR_SERVICE) as Vibrator).also {
                            it.vibrate(vib)
                        }  //设置手机振动
                        binding.textView2.text = "vibrating"
                    }else{
                        binding.textView2.text = "static"
                    }
            false
        })
    }
}
