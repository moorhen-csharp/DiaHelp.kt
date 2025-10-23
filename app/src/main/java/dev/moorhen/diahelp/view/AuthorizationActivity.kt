package dev.moorhen.diahelp.view

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.viewmodel.AuthorizationViewModel

class AuthorizationActivity : AppCompatActivity() {

    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Автоматический вход
        if (viewModel.isUserLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_authorization)

        val loginInput = findViewById<EditText>(R.id.textLogin)
        val passwordInput = findViewById<EditText>(R.id.textPassword)
        val loginButton = findViewById<Button>(R.id.btnAuthorization)
        val registerNav = findViewById<TextView>(R.id.tvRegister)

        registerNav.setOnClickListener { viewModel.onRegisterClicked() }

        // ✅ Наблюдение за навигацией
        viewModel.navigateToRegistration.observe(this) { navigate ->
            if (navigate) {
                startActivity(Intent(this, RegistrationActivity::class.java))
                viewModel.onNavigatedToRegistration()
            }
        }

        // ✅ Сообщение пользователю
        viewModel.loginResult.observe(this) { message ->
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
        }

        // ✅ Навигация при успешном входе
        viewModel.loginSuccess.observe(this) { success ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        // ✅ Кнопка входа
        loginButton.setOnClickListener {
            val username = loginInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Введите логин и пароль", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(username, password)
        }
    }
}
