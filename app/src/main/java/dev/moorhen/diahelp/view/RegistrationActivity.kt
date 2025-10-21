package dev.moorhen.diahelp.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.viewmodel.RegistrationViewModel

class RegistrationActivity : AppCompatActivity() {
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val username = findViewById<EditText>(R.id.textLogin)
        val email = findViewById<EditText>(R.id.textEmail)
        val coeff = findViewById<EditText>(R.id.textCorrection)
        val password = findViewById<EditText>(R.id.textPassword)
        val confirm = findViewById<EditText>(R.id.textConfirmPassword)
        val registerButton = findViewById<Button>(R.id.btnRegistration)

        registerButton.setOnClickListener {
            val coeffValue = coeff.text.toString().toDoubleOrNull() ?: 0.0

            viewModel.registerUser(
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                confirm.text.toString(),
                coeffValue
            ) { success, message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                if (success) finish()
            }
        }
    }
}
