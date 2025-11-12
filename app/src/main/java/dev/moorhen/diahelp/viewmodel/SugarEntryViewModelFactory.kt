package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.moorhen.diahelp.data.repository.SugarRepository

class SugarEntryViewModelFactory(
    private val repository: SugarRepository,
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SugarEntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SugarEntryViewModel(repository, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
