package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.moorhen.diahelp.R

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _selectedFragment = MutableLiveData<Int>()
    val selectedFragment: LiveData<Int> get() = _selectedFragment

    fun selectFragment(menuItemId: Int) {
        _selectedFragment.value = menuItemId
    }

    init {
        _selectedFragment.value = R.id.navigation_correction
    }
}
