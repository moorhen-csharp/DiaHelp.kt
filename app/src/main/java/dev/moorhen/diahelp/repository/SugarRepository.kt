package dev.moorhen.diahelp.data.repository

import android.content.Context
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.data.model.SugarModel
import java.util.*

class SugarRepository(context: Context) {

    private val dao = AppDatabase.getDatabase(context).sugarDao()

    suspend fun insert(note: SugarModel) = dao.insert(note)

    // Старый метод: возвращает все записи из БД (теперь не нужен для UI)
    // suspend fun getAllSugarNotes() = dao.getAll()

    // Новый метод: возвращает записи конкретного пользователя
    suspend fun getAllSugarNotesByUserId(userId: Int) = dao.getAllByUserId(userId)

    // Старый метод: очищает всю таблицу (не рекомендуется)
    // suspend fun clearAll() = dao.clear()

    // Новый метод: очищает записи конкретного пользователя
    suspend fun clearAllForUser(userId: Int) = dao.clearByUserId(userId)

    // Обновленный метод: возвращает записи конкретного пользователя за период
    suspend fun getNotesByPeriod(userId: Int, startDate: Date, endDate: Date) =
        dao.getByPeriod(userId, startDate, endDate)
}