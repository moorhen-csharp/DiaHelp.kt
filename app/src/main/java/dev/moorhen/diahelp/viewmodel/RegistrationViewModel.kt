package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.data.model.UserModel
import dev.moorhen.diahelp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(AppDatabase.getDatabase(application).userDao())

    fun registerUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        coeffInsulin: Double,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            // Проверяем заполнение
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                onResult(false, "Заполните все поля")
                return@launch
            }

            // Проверяем совпадение паролей
            if (password != confirmPassword) {
                onResult(false, "Пароли не совпадают")
                return@launch
            }

            // Проверяем существующего пользователя
            val existingUser = withContext(Dispatchers.IO) {
                repository.getUserByUsernameOrEmail(username, email)
            }

            if (existingUser != null) {
                onResult(false, "Пользователь с таким логином или email уже существует")
                return@launch
            }

            // Создаем нового пользователя
            val newUser = UserModel(
                username = username,
                email = email,
                password = password,
                coeffInsulin = coeffInsulin
            )

            // Добавляем пользователя в базу
            withContext(Dispatchers.IO) {
                repository.insertUser(newUser)
            }

            onResult(true, "Регистрация успешна")
        }
    }
}
