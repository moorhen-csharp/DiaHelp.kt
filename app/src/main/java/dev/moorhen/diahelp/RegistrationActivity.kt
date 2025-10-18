package dev.moorhen.diahelp

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        val loginLayout = findViewById<TextInputLayout>(R.id.Login)
        val passwordLayout = findViewById<TextInputLayout>(R.id.Password)
        val whiteColor = ContextCompat.getColor(this, R.color.white)

        loginLayout.boxStrokeColor = whiteColor
        loginLayout.hintTextColor = ColorStateList.valueOf(whiteColor)

        passwordLayout.boxStrokeColor = whiteColor
        passwordLayout.hintTextColor = ColorStateList.valueOf(whiteColor)
    }
}
