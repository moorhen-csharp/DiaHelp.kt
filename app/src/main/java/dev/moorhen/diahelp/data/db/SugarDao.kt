package dev.moorhen.diahelp.data.db

import androidx.room.*
import dev.moorhen.diahelp.data.model.SugarModel
import java.util.*

@Dao
interface SugarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: SugarModel)

    // Получить все записи для конкретного пользователя
    @Query("SELECT * FROM sugar_records WHERE userId = :userId ORDER BY Date DESC")
    suspend fun getAllByUserId(userId: Int): List<SugarModel>

    // Получить все записи (все равно будет использоваться редко)
    @Query("SELECT * FROM sugar_records ORDER BY Date DESC")
    suspend fun getAll(): List<SugarModel>

    @Query("DELETE FROM sugar_records")
    suspend fun clear()

    // Получить записи по периоду для конкретного пользователя
    @Query("SELECT * FROM sugar_records WHERE userId = :userId AND Date BETWEEN :startDate AND :endDate ORDER BY Date DESC")
    suspend fun getByPeriod(userId: Int, startDate: Date, endDate: Date): List<SugarModel>

    // Удалить конкретную запись (может понадобиться)
    @Delete
    suspend fun delete(note: SugarModel)

    // Удалить все записи конкретного пользователя (для очистки профиля)
    @Query("DELETE FROM sugar_records WHERE userId = :userId")
    suspend fun clearByUserId(userId: Int)
}