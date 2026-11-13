package com.esqroot.sprint1

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupCheckBoxes()
        configurarBotonGuardar()
    }

    private fun configurarBotonGuardar() {
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val cbAndroid = findViewById<CheckBox>(R.id.cbAndroid)
        val cbReact = findViewById<CheckBox>(R.id.cbReact)
        val cbFlutter = findViewById<CheckBox>(R.id.cbFlutter)
        val rgModalidad = findViewById<RadioGroup>(R.id.rgModalidad)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val txtResultado = findViewById<TextView>(R.id.txtResultado)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            if (nombre.isEmpty()) {
                etNombre.error = "Escribe tu nombre"
                return@setOnClickListener
            }

            val tecnologias = listOf(cbAndroid, cbReact, cbFlutter)
                .filter { it.isChecked }
                .joinToString(", ") { it.text }

            val modalidad = when (rgModalidad.checkedRadioButtonId) {
                R.id.rbPresencial -> "Presencial"
                R.id.rbOnline -> "Online"
                R.id.rbMixta -> "Mixta"
                else -> "sin elegir"
            }

            txtResultado.text = "Nombre: $nombre\n" +
                    "Tecnologías: ${tecnologias.ifEmpty { "ninguna" }}\n" +
                    "Modalidad: $modalidad"
        }
    }


    private fun setupCheckBoxes() {
        val cbAndroid = findViewById<CheckBox>(R.id.cbAndroid)
        val cbReact = findViewById<CheckBox>(R.id.cbReact)
        val cbFlutter = findViewById<CheckBox>(R.id.cbFlutter)

        val listener = CompoundButton.OnCheckedChangeListener { button, isChecked ->
            val state = if (isChecked) "checked" else "unchecked"
            Toast.makeText(this, "${button.text} $state", Toast.LENGTH_SHORT).show()
        }

        cbAndroid.setOnCheckedChangeListener(listener)
        cbReact.setOnCheckedChangeListener(listener)
        cbFlutter.setOnCheckedChangeListener(listener)
    }

}
