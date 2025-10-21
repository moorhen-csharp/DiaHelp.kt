package dev.moorhen.diahelp.view

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.viewmodel.AuthorizationViewModel

class AuthorizationActivity : AppCompatActivity() {

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authorization)

        val loginLayout = findViewById<TextInputLayout>(R.id.Login)
        val passwordLayout = findViewById<TextInputLayout>(R.id.Password)
        val regNav = findViewById<TextView>(R.id.tvRegister)
        val btnLogin = findViewById<Button>(R.id.btnAuthorization)
        val etLogin = findViewById<EditText>(R.id.textLogin)
        val etPassword = findViewById<EditText>(R.id.textPassword)
        val whiteColor = ContextCompat.getColor(this, R.color.white)

        // Настройка цветов
        listOf(loginLayout, passwordLayout).forEach {
            it.boxStrokeColor = whiteColor
            it.hintTextColor = ColorStateList.valueOf(whiteColor)
        }

        // Кнопка "Войти"
        btnLogin.setOnClickListener {
            val username = etLogin.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(username, password)
            }
        }

        // Ссылка "Зарегистрироваться"
        regNav.setOnClickListener {
            viewModel.onRegisterClicked()
        }

        // Наблюдение за результатом логина
        viewModel.loginResult.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (message.contains("успешно", ignoreCase = true)) {
                // Здесь потом можно перейти на HomeActivity
            }
        }

        // Наблюдение за переходом на регистрацию
        viewModel.navigateToRegistration.observe(this) { navigate ->
            if (navigate) {
                startActivity(Intent(this, RegistrationActivity::class.java))
                viewModel.onNavigatedToRegistration()
            }
        }
    }
}
