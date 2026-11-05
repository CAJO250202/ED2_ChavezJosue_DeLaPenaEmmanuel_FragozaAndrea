package com.example.ed2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnAcelerometro: Button
    private lateinit var btnPresion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnAcelerometro = findViewById(R.id.btnSensorAcelerometro)
        btnPresion = findViewById(R.id.btnSensorPresion)

        btnAcelerometro.setOnClickListener {
            val intent = Intent(this, AcelerometroActivity::class.java)
            startActivity(intent)
        }

        btnPresion.setOnClickListener {
            val intent = Intent(this, PresionActivity::class.java)
            startActivity(intent)
        }

        val mainView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}