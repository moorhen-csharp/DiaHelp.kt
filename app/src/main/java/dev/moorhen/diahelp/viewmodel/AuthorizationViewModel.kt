package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.repository.UserRepository
import dev.moorhen.diahelp.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(AppDatabase.getDatabase(application).userDao())
    private val sessionManager = SessionManager(application)

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _navigateToRegistration = MutableLiveData<Boolean>()
    val navigateToRegistration: LiveData<Boolean> get() = _navigateToRegistration

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                repository.loginUser(username, password)
            }

            if (user != null) {
                // Передаем user.id в качестве первого аргумента
                sessionManager.saveUser(user.id, user.username, user.email, user.coeffInsulin)
                _loginSuccess.postValue(true)
                _loginResult.postValue("Вход выполнен успешно")
            } else {
                _loginSuccess.postValue(false)
                _loginResult.postValue("Неверный логин или пароль")
            }
        }
    }

    fun onRegisterClicked() {
        _navigateToRegistration.value = true
    }

    fun onNavigatedToRegistration() {
        _navigateToRegistration.value = false
    }

    fun isUserLoggedIn(): Boolean = sessionManager.isLoggedIn()
}
