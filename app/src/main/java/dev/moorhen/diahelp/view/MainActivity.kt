package dev.moorhen.diahelp.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.utils.showIncorrectToast

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
        val currentGlucose: EditText = findViewById(R.id.current_glucose)
        val correctionInsulin: TextView = findViewById(R.id.correction_insulin)
        val targetGlucose: EditText = findViewById(R.id.target_glucose)
        val resultBtn: Button = findViewById(R.id.result)

        currentGlucose.setText("0")
        correctionInsulin.setText("0")
        targetGlucose.setText("5")

        resultBtn.setOnClickListener {
            calculateCorrectioninsulin()
        }
    }
    fun calculateCorrectioninsulin() {
        val currentGlucose: EditText = findViewById(R.id.current_glucose)
        val targetGlucose: EditText = findViewById(R.id.target_glucose)
        val correctionInsulin: TextView = findViewById(R.id.correction_insulin)

        val currentValue = currentGlucose.text.toString().toDoubleOrNull()
        val targetValue = targetGlucose.text.toString().toDoubleOrNull()

        if (currentValue != null && targetValue != null && currentValue > 0 && targetValue > 0) {
            val correction = (currentValue - targetValue) / 2
            correctionInsulin.text = String.format("%.1f", correction)
        } else {
            val toast = Toast.makeText(this, "This is a custom Toast", Toast.LENGTH_SHORT)
            toast.showIncorrectToast("Некорректное значение!", this)
        }
    }
}