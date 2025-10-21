package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.data.model.UserModel
import dev.moorhen.diahelp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val allUsers: LiveData<List<UserModel>>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.getAllUsers()
    }

    fun registerUser(user: UserModel, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val existing = repository.getUserByUsernameOrEmail(user.username, user.email)
            if (existing != null) {
                onResult(false, "Пользователь с таким логином или email уже существует")
            } else {
                repository.insertUser(user)
                onResult(true, "Регистрация успешна")
            }
        }
    }

    fun login(username: String, password: String, onResult: (Boolean, UserModel?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.loginUser(username, password)
            if (user != null) onResult(true, user)
            else onResult(false, null)
        }
    }
}
