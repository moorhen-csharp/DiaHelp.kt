package dev.moorhen.diahelp.data.dao

import androidx.room.*
import dev.moorhen.diahelp.data.model.SugarModel
import java.util.*

@Dao
interface SugarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: SugarModel)

    @Query("SELECT * FROM sugar_note ORDER BY Date DESC")
    suspend fun getAll(): List<SugarModel>

    @Query("DELETE FROM sugar_note")
    suspend fun clear()

    @Query("SELECT * FROM sugar_note WHERE Date BETWEEN :start AND :end ORDER BY Date DESC")
    suspend fun getByPeriod(start: Date, end: Date): List<SugarModel>
}
