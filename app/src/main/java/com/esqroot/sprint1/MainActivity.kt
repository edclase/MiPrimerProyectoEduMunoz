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
        setupSaveButton()
    }

    // Feature 1: the SeekBar updates the level text
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

    // Feature 3: the Save button collects the data and shows it in txtResult
    private fun setupSaveButton() {
        val etName = findViewById<EditText>(R.id.etName)
        val cbAndroid = findViewById<CheckBox>(R.id.cbAndroid)
        val cbReact = findViewById<CheckBox>(R.id.cbReact)
        val cbFlutter = findViewById<CheckBox>(R.id.cbFlutter)
        val rgMode = findViewById<RadioGroup>(R.id.rgMode)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isEmpty()) {
                etName.error = "Enter your name"
                return@setOnClickListener
            }

            val technologies = listOf(cbAndroid, cbReact, cbFlutter)
                .filter { it.isChecked }
                .joinToString(", ") { it.text }

            val mode = when (rgMode.checkedRadioButtonId) {
                R.id.rbOnSite -> "On-site"
                R.id.rbOnline -> "Online"
                R.id.rbHybrid -> "Hybrid"
                else -> "not selected"
            }

            txtResult.text = "Name: $name\n" +
                    "Technologies: ${technologies.ifEmpty { "none" }}\n" +
                    "Mode: $mode"
        }
    }

    // Feature 2: each CheckBox shows a Toast when it is checked or unchecked
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
