package dev.moorhen.diahelp.view

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.View.RegistrationActivity
import dev.moorhen.diahelp.ViewModel.AuthorizationViewModel

class AuthorizationActivity : AppCompatActivity() {

    // Подключаем ViewModel
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authorization)

        // ---------- UI настройка ----------
        val loginLayout = findViewById<TextInputLayout>(R.id.Login)
        val passwordLayout = findViewById<TextInputLayout>(R.id.Password)
        val whiteColor = ContextCompat.getColor(this, R.color.white)
        val regNav: TextView = findViewById(R.id.tvRegister)

        loginLayout.boxStrokeColor = whiteColor
        loginLayout.hintTextColor = ColorStateList.valueOf(whiteColor)
        passwordLayout.boxStrokeColor = whiteColor
        passwordLayout.hintTextColor = ColorStateList.valueOf(whiteColor)

        // ---------- Обработка нажатий ----------
        regNav.setOnClickListener {
            viewModel.onRegisterClicked()
        }

        // ---------- Наблюдение за событиями из ViewModel ----------
        viewModel.navigateToRegistration.observe(this) { navigate ->
            if (navigate) {
                startActivity(Intent(this, RegistrationActivity::class.java))
                viewModel.onNavigatedToRegistration()
            }
        }
    }
}
