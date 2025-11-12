package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dev.moorhen.diahelp.data.model.SugarModel
import dev.moorhen.diahelp.data.repository.SugarRepository
import kotlinx.coroutines.launch
import java.util.*

class SugarNoteViewModel(private val repository: SugarRepository, app: Application) : AndroidViewModel(app) {

    val sugarNotes = MutableLiveData<List<SugarModel>>()
    val average = MutableLiveData<Double>()
    val selectedPeriod = MutableLiveData("1 День")
    val isSugarListVisible = MutableLiveData(true)
    val isEmpty = MutableLiveData(true)

    init {
        loadSugarNotes()
    }

    fun loadSugarNotes() {
        viewModelScope.launch {
            val list = repository.getAllSugarNotes()
            sugarNotes.postValue(list)
            isEmpty.postValue(list.isEmpty())
            calculateAverage()
        }
    }

    fun clearNotes() {
        viewModelScope.launch {
            repository.clearAll()
            loadSugarNotes()
        }
    }

    fun calculateAverage() {
        viewModelScope.launch {
            val endDate = Date()
            val startDate = when (selectedPeriod.value) {
                "1 День" -> Date(endDate.time - 86400000L)
                "3 Месяца" -> Date(endDate.time - 90L * 86400000L)
                "6 Месяцев" -> Date(endDate.time - 180L * 86400000L)
                "1 Год" -> Date(endDate.time - 365L * 86400000L)
                else -> Date()
            }
            val notes = repository.getNotesByPeriod(startDate, endDate)
            val valid = notes.filter { it.SugarLevel != -1.0 }
            val avg = if (valid.isNotEmpty()) valid.map { it.SugarLevel }.average() else 0.0
            average.postValue(avg)
        }
    }

    fun toggleToSugar() {
        isSugarListVisible.value = true
    }

    fun toggleToFood() {
        isSugarListVisible.value = false
    }
}
