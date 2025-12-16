// dev.moorhen.diahelp.viewmodel.SugarNoteViewModel
import android.app.Application
import androidx.lifecycle.*
import dev.moorhen.diahelp.data.model.SugarModel
import dev.moorhen.diahelp.data.repository.SugarRepository
import dev.moorhen.diahelp.utils.SessionManager // ✅
import kotlinx.coroutines.launch
import java.util.*

class SugarNoteViewModel(
    private val repository: SugarRepository,
    app: Application,
    private val sessionManager: SessionManager
) : AndroidViewModel(app) {

    val sugarNotes = MutableLiveData<List<SugarModel>>()
    val average = MutableLiveData<Double>()
    val selectedPeriod = MutableLiveData("1 День")
    val isSugarListVisible = MutableLiveData(true)
    val isEmpty = MutableLiveData(true)

    init {
        loadSugarNotes()
    }

    fun loadSugarNotes() {
        // ✅ Проверяем, залогинен ли пользователь
        val userId = sessionManager.getUserId()
        if (userId == -1) {
            sugarNotes.postValue(emptyList())
            isEmpty.postValue(true)
            return
        }

        viewModelScope.launch {
            val list = repository.getAllSugarNotesByUserId(userId)
            sugarNotes.postValue(list)
            isEmpty.postValue(list.isEmpty())
            calculateAverage()
        }
    }

    fun clearNotes() {
        val userId = sessionManager.getUserId()
        if (userId == -1) return

        viewModelScope.launch {
            repository.clearAllForUser(userId)
            loadSugarNotes()
        }
    }

    fun calculateAverage() {
        val userId = sessionManager.getUserId()
        if (userId == -1) {
            average.postValue(0.0)
            return
        }

        viewModelScope.launch {
            val endDate = Date()
            val startDate = when (selectedPeriod.value) {
                "1 День" -> Date(endDate.time - 86400000L)
                "1 Неделя" -> Date(endDate.time - 7L * 86400000L)
                "1 Месяц" -> Date(endDate.time - 30L * 86400000L)
                "3 Месяца" -> Date(endDate.time - 90L * 86400000L)
                "6 Месяцев" -> Date(endDate.time - 180L * 86400000L)
                "1 Год" -> Date(endDate.time - 365L * 86400000L)
                else -> Date()
            }
            val notes = repository.getNotesByPeriod(userId, startDate, endDate)
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