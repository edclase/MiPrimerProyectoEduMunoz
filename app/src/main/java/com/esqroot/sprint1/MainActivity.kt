package com.esqroot.sprint1

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
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
        setupSeekBar()
        setupCheckBoxes()
        configurarBotonGuardar()
    }

    private fun setupSeekBar() {
        val sbLevel = findViewById<SeekBar>(R.id.sbLevel)
        val txtLevel = findViewById<TextView>(R.id.txtLevel)

        sbLevel.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                txtLevel.text = "Selected level: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun configurarBotonGuardar() {
        val etNombre = findViewById<EditText>(R.id.etName)
        val cbAndroid = findViewById<CheckBox>(R.id.cbAndroid)
        val cbReact = findViewById<CheckBox>(R.id.cbReact)
        val cbFlutter = findViewById<CheckBox>(R.id.cbFlutter)
        val rgModalidad = findViewById<RadioGroup>(R.id.rgMode)
        val btnGuardar = findViewById<Button>(R.id.btnSave)
        val txtResultado = findViewById<TextView>(R.id.txtResult)

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
                R.id.rbOnSite -> "Presencial"
                R.id.rbOnline -> "Online"
                R.id.rbHybrid -> "Mixta"
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
