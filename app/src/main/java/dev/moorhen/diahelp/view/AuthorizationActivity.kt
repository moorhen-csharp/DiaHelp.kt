package dev.moorhen.diahelp.view

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.viewmodel.AuthorizationViewModel

class AuthorizationActivity : AppCompatActivity() {

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        val loginInput = findViewById<EditText>(R.id.textLogin)
        val passwordInput = findViewById<EditText>(R.id.textPassword)
        val loginButton = findViewById<Button>(R.id.btnAuthorization)
        val registerNav = findViewById<TextView>(R.id.tvRegister)

        // Навигация на регистрацию
        registerNav.setOnClickListener {
            viewModel.onRegisterClicked()
        }

        // Подписка на навигацию
        viewModel.navigateToRegistration.observe(this, Observer { navigate ->
            if (navigate) {
                startActivity(Intent(this, RegistrationActivity::class.java))
                viewModel.onNavigatedToRegistration()
            }
        })

        // Подписка на результат логина
        viewModel.loginResult.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            if (message == "Вход выполнен успешно") {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        // Обработка нажатия на кнопку входа
        loginButton.setOnClickListener {
            val username = loginInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(username, password)
        }
    }
}
