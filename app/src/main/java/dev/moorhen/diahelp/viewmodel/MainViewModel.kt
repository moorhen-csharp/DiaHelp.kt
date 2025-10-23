package dev.moorhen.diahelp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.moorhen.diahelp.R

class MainViewModel : ViewModel() {

    private val _selectedFragment = MutableLiveData<Int>()
    val selectedFragment: LiveData<Int> get() = _selectedFragment

    fun selectFragment(menuItemId: Int) {
        _selectedFragment.value = menuItemId
    }

    // По умолчанию открываем "Главная"
    init {
        _selectedFragment.value = R.id.navigation_correction
    }
}
