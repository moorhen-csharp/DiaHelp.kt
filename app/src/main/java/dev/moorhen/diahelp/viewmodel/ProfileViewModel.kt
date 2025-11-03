package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.moorhen.diahelp.utils.SessionManager

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> get() = _logout

    fun onLogoutClicked() {
        sessionManager.logout()
        _logout.value = true
    }

    fun getUserName(): String = sessionManager.getUsername() ?: "Неизвестно"
    fun getUserEmail(): String = sessionManager.getEmail() ?: "Нет данных"
}
