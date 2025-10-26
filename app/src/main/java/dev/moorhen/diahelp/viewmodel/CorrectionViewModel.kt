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

class CorrectionViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(AppDatabase.getDatabase(application).userDao())
    private val sessionManager = SessionManager(application)

    private val _correctionResult = MutableLiveData<String>()
    val correctionResult: LiveData<String> = _correctionResult

    fun calculateInsulin(currentGlucose: Double, targetGlucose: Double) {
        viewModelScope.launch {
            val username = sessionManager.getUsername() ?: run {
                return@launch
            }

            val user = withContext(Dispatchers.IO) {
                userRepository.getUserByUsernameOrEmail(username, username)
            }

            if (user == null) {
                return@launch
            }

            if (currentGlucose <= 0 || targetGlucose <= 0) {
                return@launch
            }

            val coeffInsulin = if (user.coeffInsulin > 0) user.coeffInsulin else 2.0
            val correction = (currentGlucose - targetGlucose) / coeffInsulin
            val finalValue = if (correction < 0) 0.0 else correction

            _correctionResult.postValue("%.1f".format(finalValue))
        }
    }
}
