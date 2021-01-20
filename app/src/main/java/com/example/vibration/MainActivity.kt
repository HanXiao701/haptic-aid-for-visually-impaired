package com.example.vibration

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.goPatternButton.setOnClickListener {
            val intent = Intent(this@MainActivity, patternTestActivity::class.java)
            startActivity(intent)
        }
        binding.goNeedleButton.setOnClickListener {
            val intent = Intent(this@MainActivity, needleTestActivity::class.java)
            startActivity(intent)
        }

        binding.goNeedleVectorButton.setOnClickListener {
            val intent = Intent(this@MainActivity, needleVectorTestActivity::class.java)
            startActivity(intent)
        }

        binding.goCompassButton.setOnClickListener {
            val intent = Intent(this@MainActivity, CompassTestActivity::class.java)
            startActivity(intent)
        }
    }
}
