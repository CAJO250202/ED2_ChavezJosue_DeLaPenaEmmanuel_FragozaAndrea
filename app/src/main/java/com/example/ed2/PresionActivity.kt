package com.example.ed2

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PresionActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tvTitulo: TextView
    private lateinit var tvValores: TextView
    private lateinit var tvEstado: TextView
    private lateinit var btnContadores: Button
    private lateinit var mainLayout: LinearLayout

    private lateinit var sensorManager: SensorManager
    private var sensorPresion: Sensor? = null

    // Contadores para cada caso de presión
    private var contadorBaja = 0
    private var contadorNormal = 0
    private var contadorAlta = 0
    private var estadoActual: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_presion)

        tvTitulo = findViewById(R.id.tvTitulo)
        tvEstado = findViewById(R.id.tvEstado)
        tvValores = findViewById(R.id.tvValores)
        btnContadores = findViewById(R.id.btnContadores)
        mainLayout = findViewById(R.id.mainLayout)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorPresion = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        if (sensorPresion == null) {
            tvEstado.text = "No hay sensor de presión :("
        }

        btnContadores.setOnClickListener {
            val mensaje = "Contadores:\nBaja: $contadorBaja | Normal: $contadorNormal | Alta: $contadorAlta"
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPresion)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PRESSURE) {
            val presion = event.values[0]
            tvValores.text = "Presión: $presion hPa"

            // Evaluación de los rangos de presión
            if (presion < 1000f) {
                if (estadoActual != "BAJA") {
                    estadoActual = "BAJA"
                    contadorBaja++
                    tvEstado.text = "Presión baja"
                    mainLayout.setBackgroundColor(Color.BLUE)
                    tvTitulo.setTextColor(Color.WHITE)
                    tvValores.setTextColor(Color.WHITE)
                    tvEstado.setTextColor(Color.WHITE)
                }
            } else if (presion <= 1020f) {
                if (estadoActual != "NORMAL") {
                    estadoActual = "NORMAL"
                    contadorNormal++
                    tvEstado.text = "Presión normal"
                    mainLayout.setBackgroundColor(Color.GREEN)
                    tvTitulo.setTextColor(Color.BLACK)
                    tvValores.setTextColor(Color.BLACK)
                    tvEstado.setTextColor(Color.BLACK)
                }
            } else {
                if (estadoActual != "ALTA") {
                    estadoActual = "ALTA"
                    contadorAlta++
                    tvEstado.text = "Presión alta"
                    mainLayout.setBackgroundColor(Color.YELLOW)
                    tvTitulo.setTextColor(Color.BLACK)
                    tvValores.setTextColor(Color.BLACK)
                    tvEstado.setTextColor(Color.BLACK)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onResume() {
        super.onResume()
        sensorPresion?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}