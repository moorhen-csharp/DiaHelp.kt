package dev.moorhen.diahelp.data.repository

import android.content.Context
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.data.model.SugarModel
import java.util.*

class SugarRepository(context: Context) {

    private val dao = AppDatabase.getDatabase(context).sugarDao()

    suspend fun insert(note: SugarModel) = dao.insert(note)
    suspend fun getAllSugarNotes() = dao.getAll()
    suspend fun clearAll() = dao.clear()
    suspend fun getNotesByPeriod(startDate: Date, endDate: Date) = dao.getByPeriod(startDate, endDate)
}
