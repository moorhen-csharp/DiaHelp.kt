package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    private val _navigateToRegistration = MutableLiveData<Boolean>()
    val navigateToRegistration: LiveData<Boolean> get() = _navigateToRegistration

    init {
        val dao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(dao)
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                repository.loginUser(username, password)
            }

            if (user != null) {
                _loginResult.value = "Вход выполнен успешно"
            } else {
                _loginResult.value = "Неверный логин или пароль"
            }
        }
    }

    fun onRegisterClicked() {
        _navigateToRegistration.value = true
    }

    fun onNavigatedToRegistration() {
        _navigateToRegistration.value = false
    }
}
