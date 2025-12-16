// dev.moorhen.diahelp.viewmodel.SugarEntryViewModelFactory
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.moorhen.diahelp.data.repository.SugarRepository
import dev.moorhen.diahelp.utils.SessionManager // ✅

class SugarEntryViewModelFactory(
    private val repository: SugarRepository,
    private val app: Application,
    private val sessionManager: SessionManager // ✅ Добавлен SessionManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SugarEntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SugarEntryViewModel(repository, app, sessionManager) as T // ✅ Передаем sessionManager
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}