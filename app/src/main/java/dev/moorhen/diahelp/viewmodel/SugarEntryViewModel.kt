package dev.moorhen.diahelp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.moorhen.diahelp.data.model.SugarModel
import dev.moorhen.diahelp.data.repository.SugarRepository
import kotlinx.coroutines.launch
import java.util.*

class SugarEntryViewModel(private val repository: SugarRepository, app: Application) : AndroidViewModel(app) {
    var sugarLevel: Double? = null
    var insulinDose: Double? = null
    var selectedSugarType: String? = null
    var selectedHealthType: String? = null
    var isNotMeasured = false

    fun notMeasured() {
        isNotMeasured = true
        sugarLevel = -1.0
    }

    fun saveNote(): Boolean {
        val ctx = getApplication<Application>()

        if (selectedSugarType == null) {
            Toast.makeText(ctx, "Выберите тип измерения", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedHealthType == null) {
            Toast.makeText(ctx, "Выберите самочувствие", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isNotMeasured && (sugarLevel == null || sugarLevel!! <= 0)) {
            Toast.makeText(ctx, "Введите корректный уровень сахара", Toast.LENGTH_SHORT).show()
            return false
        }

        val note = SugarModel(
            SugarLevel = sugarLevel ?: -1.0,
            MeasurementTime = selectedSugarType ?: "",
            HealthType = selectedHealthType ?: "",
            InsulinDose = insulinDose ?: 0.0,
            Date = Date()
        )

        viewModelScope.launch {
            repository.insert(note)
        }

        return true
    }

}
