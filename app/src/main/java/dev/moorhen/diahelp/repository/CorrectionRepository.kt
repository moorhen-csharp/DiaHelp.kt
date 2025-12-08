package dev.moorhen.diahelp.repository

import android.content.Context
import dev.moorhen.diahelp.data.db.AppDatabase
import dev.moorhen.diahelp.data.model.CorrectionModel
import dev.moorhen.diahelp.data.model.SugarModel
import java.util.Date

class CorrectionRepository(context: Context) {

    private val dao = AppDatabase.getDatabase(context).correctionDao()

    suspend fun insert(note: CorrectionModel) = dao.insert(note)
    suspend fun getAllSugarNotes() = dao.getAll()
    suspend fun clearAll() = dao.clear()
    suspend fun getNotesByPeriod(startDate: Date, endDate: Date) = dao.getByPeriod(startDate, endDate)
}