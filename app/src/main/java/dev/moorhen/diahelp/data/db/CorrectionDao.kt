package dev.moorhen.diahelp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.moorhen.diahelp.data.model.CorrectionModel
import dev.moorhen.diahelp.data.model.SugarModel
import java.util.Date

@Dao
interface CorrectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: CorrectionModel)

    @Query("SELECT * FROM correction ORDER BY Date DESC")
    suspend fun getAll(): List<CorrectionModel>

    @Query("DELETE FROM correction")
    suspend fun clear()

    @Query("SELECT * FROM correction WHERE Date BETWEEN :start AND :end ORDER BY Date DESC")
    suspend fun getByPeriod(start: Date, end: Date): List<CorrectionModel>
}