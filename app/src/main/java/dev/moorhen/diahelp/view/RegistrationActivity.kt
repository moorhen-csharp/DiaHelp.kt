package dev.moorhen.diahelp.view

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.viewmodel.RegistrationViewModel

class RegistrationActivity : AppCompatActivity() {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val loginInput = findViewById<EditText>(R.id.textLogin)
        val emailInput = findViewById<EditText>(R.id.textEmail)
        val correctionInput = findViewById<EditText>(R.id.textCorrection)
        val passwordInput = findViewById<EditText>(R.id.textPassword)
        val confirmPasswordInput = findViewById<EditText>(R.id.textConfirmPassword)
        val registerButton = findViewById<Button>(R.id.btnRegistration)

        registerButton.setOnClickListener {
            val username = loginInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val correction = correctionInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            val coeffInsulin = correction.toDoubleOrNull() ?: 0.0

            viewModel.registerUser(
                username,
                email,
                password,
                confirmPassword,
                coeffInsulin
            ) { success, message ->
                runOnUiThread {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    if (success) {
                        startActivity(Intent(this, AuthorizationActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}
