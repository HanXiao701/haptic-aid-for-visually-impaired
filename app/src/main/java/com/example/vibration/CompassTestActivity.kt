package com.example.vibration

import android.annotation.SuppressLint
import android.os.*
import android.os.VibrationEffect.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.vibration.databinding.ActivityCompassTestBinding

class CompassTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompassTestBinding
    var degree = 0
    var dura = 100
    var ampli = 100
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compass_test)
        binding.drawRegion.setBackgroundResource(R.drawable.ic_launcher_background)
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
            //val vib: VibrationEffect = VibrationEffect.createOneShot(dura.toLong(), ampli)
            //binding.textView.text = "straight"
            judgeTouch(event.x, event.y)
/*            if (judgeTouch(event.x, event.y)){
                val vibrator = (getSystemService(VIBRATOR_SERVICE) as Vibrator).also {
                    it.vibrate(vib)
                };  //设置手机振动
                binding.textView2.text = "vibrating"
            }else{
                binding.textView2.text = "static"
            }*/
            false
        })
    }

    @SuppressLint("NewApi", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun judgeTouch(inputX: Float, inputY: Float){
        //val vib: VibrationEffect = VibrationEffect.createOneShot(dura.toLong(), ampli)
        var vib: VibrationEffect
        if((inputX <= binding.drawRegion.layoutParams.width / 3 || inputX >= binding.drawRegion.layoutParams.width * 2 / 3)
            && inputY <= binding.drawRegion.layoutParams.height / 2){
            binding.textView2.text = "discrete-vibrating"
            vib = createPredefined(EFFECT_DOUBLE_CLICK)
            val vibrator = (getSystemService(VIBRATOR_SERVICE) as Vibrator).also {
                it.vibrate(vib)
            }  //设置手机振动
        }else if (inputY <= binding.drawRegion.layoutParams.height / 2){
            binding.textView2.text = "continuous-vibrating"
            vib = createOneShot(dura.toLong(), ampli)
            val vibrator = (getSystemService(VIBRATOR_SERVICE) as Vibrator).also {
                it.vibrate(vib)
            }  //设置手机振动
        }
    }
}
