package com.example.semana8

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "calculadora_channel",
                "Notificaciones Calculadora",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para avisos de la Calculadora Binaria"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }


        val rbDecimal = findViewById<RadioButton>(R.id.rbDecimal)
        val rbBinario = findViewById<RadioButton>(R.id.rbBinario)
        val etA = findViewById<EditText>(R.id.etA)
        val etB = findViewById<EditText>(R.id.etB)
        val btnSumar = findViewById<Button>(R.id.btnSumar)
        val btnRestar = findViewById<Button>(R.id.btnRestar)
        val etDecimal = findViewById<EditText>(R.id.etDecimal)
        val etBin = findViewById<EditText>(R.id.etBin)
        val btnDecToBin = findViewById<Button>(R.id.btnDecToBin)
        val btnBinToDec = findViewById<Button>(R.id.btnBinToDec)
        val tvResultadoDecimal = findViewById<TextView>(R.id.tvResultadoDecimal)
        val tvResultadoBinario = findViewById<TextView>(R.id.tvResultadoBinario)


        btnSumar.setOnClickListener {
            val a = etA.text.toString()
            val b = etB.text.toString()
            if (a.isEmpty() || b.isEmpty()) {
                Toast.makeText(this, "Completa ambos números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultado: Int

            if (rbDecimal.isChecked) {
                resultado = a.toInt() + b.toInt()
            } else {
                if (!esBinario(a) || !esBinario(b)) {
                    Toast.makeText(this, "Solo 0 y 1 en modo binario", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                resultado = Integer.parseInt(a, 2) + Integer.parseInt(b, 2)
            }

            tvResultadoDecimal.text = "Decimal: $resultado"
            tvResultadoBinario.text = "Binario: ${Integer.toBinaryString(resultado)}"


            mostrarNotificacion("Suma realizada", "El resultado en Decimal es $resultado")
        }

        btnRestar.setOnClickListener {
            val a = etA.text.toString()
            val b = etB.text.toString()
            if (a.isEmpty() || b.isEmpty()) {
                Toast.makeText(this, "Completa ambos números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultado: Int

            if (rbDecimal.isChecked) {
                resultado = a.toInt() - b.toInt()
            } else {
                if (!esBinario(a) || !esBinario(b)) {
                    Toast.makeText(this, "Solo 0 y 1 en modo binario", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                resultado = Integer.parseInt(a, 2) - Integer.parseInt(b, 2)
            }

            tvResultadoDecimal.text = "Decimal: $resultado"
            tvResultadoBinario.text = "Binario: ${Integer.toBinaryString(resultado)}"

            // Notificación al realizar la resta
            mostrarNotificacion("Resta realizada", "El resultado en Decimal es $resultado")
        }

        btnDecToBin.setOnClickListener {
            val dec = etDecimal.text.toString()
            if (dec.isEmpty()) {
                Toast.makeText(this, "Ingresa un número decimal", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val bin = Integer.toBinaryString(dec.toInt())
            tvResultadoDecimal.text = "Decimal: $dec"
            tvResultadoBinario.text = "Binario: $bin"

            mostrarNotificacion("Conversión realizada", "$dec en binario es $bin")
        }

        btnBinToDec.setOnClickListener {
            val bin = etBin.text.toString()
            if (!esBinario(bin)) {
                Toast.makeText(this, "Número binario inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val dec = Integer.parseInt(bin, 2)
            tvResultadoDecimal.text = "Decimal: $dec"
            tvResultadoBinario.text = "Binario: $bin"

            mostrarNotificacion("Conversión realizada", "$bin en decimal es $dec")
        }
    }

    private fun mostrarNotificacion(titulo: String, mensaje: String) {
        val builder = NotificationCompat.Builder(this, "calculadora_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
                return
            }
            notify((0..1000).random(), builder.build()) // ID aleatorio para no sobrescribir
        }
    }

    private fun esBinario(num: String): Boolean {
        return num.all { it == '0' || it == '1' }
    }
}
