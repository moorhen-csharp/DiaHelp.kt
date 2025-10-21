package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.data.model.UserModel
import dev.moorhen.diahelp.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val dao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(dao)
    }

    fun registerUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        coeffInsulin: Double,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            if (password != confirmPassword) {
                onResult(false, "Пароли не совпадают")
                return@launch
            }

            val user = UserModel(
                username = username,
                password = password,
                email = email,
                coeffInsulin = coeffInsulin
            )

            val success = withContext(Dispatchers.IO) {
                repository.registerUser(user)
            }

            if (success) onResult(true, "Регистрация успешна")
            else onResult(false, "Пользователь уже существует")
        }
    }
}
