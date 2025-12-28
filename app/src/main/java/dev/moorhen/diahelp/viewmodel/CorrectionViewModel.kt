package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.data.model.SugarModel
import dev.moorhen.diahelp.data.repository.SugarRepository
import dev.moorhen.diahelp.repository.UserRepository
import dev.moorhen.diahelp.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class CorrectionViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(AppDatabase.getDatabase(application).userDao())
    private val sessionManager = SessionManager(application)

    private val _correctionResult = MutableLiveData<String>()
    val correctionResult: LiveData<String> = _correctionResult

    // В CorrectionViewModel.kt

    private val _showDialog = MutableLiveData<Pair<Double, Double>>()
    val showDialog: LiveData<Pair<Double, Double>> = _showDialog

    fun calculateInsulin(currentGlucose: Double, targetGlucose: Double) {
        viewModelScope.launch {
            val username = sessionManager.getUsername()
            val user = withContext(Dispatchers.IO) {
                if (username != null)
                    userRepository.getUserByUsernameOrEmail(username, username)
                else null
            }

            val coeffInsulin = user?.coeffInsulin?.takeIf { it > 0 } ?: 2.0
            val correction = (currentGlucose - targetGlucose) / coeffInsulin
            val finalValue = if (correction < 0) 0.0 else correction

            _correctionResult.postValue("%.1f".format(finalValue))

            // Отправляем данные в LiveData, чтобы показать диалог
            _showDialog.postValue(Pair(currentGlucose, finalValue))
        }
    }

    // Новая функция для сохранения записи
    fun saveSugarNote(sugarLevel: Double, insulinDose: Double) {
        val repository = SugarRepository(getApplication())
        val userId = sessionManager.getUserId()
        val note = SugarModel(
            userId = userId, // <-- Добавлено
            SugarLevel = sugarLevel,
            MeasurementTime = "Коррекция",
            HealthType = "Не указано",
            InsulinDose = insulinDose,
            Date = Date()
        )

        viewModelScope.launch {
            repository.insert(note)
        }
    }
}


