package dev.moorhen.diahelp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        val resultBtn: Button = findViewById(R.id.result)
        val currentGlucose: EditText = findViewById(R.id.current_glucose)
        val targetGlucose: EditText = findViewById(R.id.target_glucose)
        val correctionInsulin: TextView = findViewById(R.id.correction_insulin)



        resultBtn.setOnClickListener {
            val currentValue = currentGlucose.text.toString().toDoubleOrNull()
            val targetValue = targetGlucose.text.toString().toDoubleOrNull()

            if (currentValue != null && targetValue != null && currentValue > 0 && targetValue > 0) {
                val correction = (currentValue - targetValue) / 2
                correctionInsulin.text = String.format("%.1f", correction)
            } else {
                Toast.makeText(this, "Введите корректные значения", Toast.LENGTH_SHORT).show()
            }
        }

    }
}