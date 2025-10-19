package dev.moorhen.diahelp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthorizationViewModel : ViewModel() {

    private val _navigateToRegistration = MutableLiveData<Boolean>()
    val navigateToRegistration: LiveData<Boolean> get() = _navigateToRegistration

    // Когда пользователь нажимает "Зарегистрироваться"
    fun onRegisterClicked() {
        _navigateToRegistration.value = true
    }

    // После перехода — сбрасываем флаг
    fun onNavigatedToRegistration() {
        _navigateToRegistration.value = false
    }
}
