package com.example.semana8

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos del layout
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

        // --- Operaciones básicas (Suma / Resta) ---
        btnSumar.setOnClickListener {
            val a = etA.text.toString()
            val b = etB.text.toString()
            if (a.isEmpty() || b.isEmpty()) {
                Toast.makeText(this, "Completa ambos números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (rbDecimal.isChecked) {
                val resultado = a.toInt() + b.toInt()
                tvResultadoDecimal.text = "Decimal: $resultado"
                tvResultadoBinario.text = "Binario: ${Integer.toBinaryString(resultado)}"
            } else {
                if (!esBinario(a) || !esBinario(b)) {
                    Toast.makeText(this, "Solo 0 y 1 en modo binario", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val resultado = Integer.parseInt(a, 2) + Integer.parseInt(b, 2)
                tvResultadoDecimal.text = "Decimal: $resultado"
                tvResultadoBinario.text = "Binario: ${Integer.toBinaryString(resultado)}"
            }
        }

        btnRestar.setOnClickListener {
            val a = etA.text.toString()
            val b = etB.text.toString()
            if (a.isEmpty() || b.isEmpty()) {
                Toast.makeText(this, "Completa ambos números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (rbDecimal.isChecked) {
                val resultado = a.toInt() - b.toInt()
                tvResultadoDecimal.text = "Decimal: $resultado"
                tvResultadoBinario.text = "Binario: ${Integer.toBinaryString(resultado)}"
            } else {
                if (!esBinario(a) || !esBinario(b)) {
                    Toast.makeText(this, "Solo 0 y 1 en modo binario", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val resultado = Integer.parseInt(a, 2) - Integer.parseInt(b, 2)
                tvResultadoDecimal.text = "Decimal: $resultado"
                tvResultadoBinario.text = "Binario: ${Integer.toBinaryString(resultado)}"
            }
        }

        // --- Conversión directa ---
        btnDecToBin.setOnClickListener {
            val dec = etDecimal.text.toString()
            if (dec.isEmpty()) {
                Toast.makeText(this, "Ingresa un número decimal", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val bin = Integer.toBinaryString(dec.toInt())
            tvResultadoDecimal.text = "Decimal: $dec"
            tvResultadoBinario.text = "Binario: $bin"
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
        }
    }

    private fun esBinario(num: String): Boolean {
        return num.all { it == '0' || it == '1' }
    }
}
