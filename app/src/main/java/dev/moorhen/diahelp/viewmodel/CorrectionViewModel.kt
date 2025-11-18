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

            // 1️⃣ Пытаемся получить имя пользователя
            val username = sessionManager.getUsername()

            // 2️⃣ Пытаемся получить пользователя из БД (если не найден — user = null)
            val user = withContext(Dispatchers.IO) {
                if (username != null)
                    userRepository.getUserByUsernameOrEmail(username, username)
                else null
            }

            // 3️⃣ Берём коэффициент инсулина
            // если пользователь найден → берем его коэффициент
            // если нет → берем 2.0
            val coeffInsulin = user?.coeffInsulin?.takeIf { it > 0 } ?: 2.0

            // 4️⃣ Выполняем расчёт
            val correction = (currentGlucose - targetGlucose) / coeffInsulin
            val finalValue = if (correction < 0) 0.0 else correction

            // 5️⃣ Обновляем LiveData
            _correctionResult.postValue("%.1f".format(finalValue))
        }
    }
}

