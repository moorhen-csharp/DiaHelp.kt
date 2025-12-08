package dev.moorhen.diahelp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.moorhen.diahelp.data.dao.SugarDao
import dev.moorhen.diahelp.data.model.UserModel
import dev.moorhen.diahelp.data.model.SugarModel
import dev.moorhen.diahelp.util.Converters

@Database(
    entities = [UserModel::class, SugarModel::class], // ✅ добавили SugarModel
    version = 2, // 🔺 увеличь версию БД, чтобы Room пересоздал таблицы
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun sugarDao(): SugarDao
    abstract fun correctionDao(): CorrectionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "diahelp_db"
                )
                    .fallbackToDestructiveMigration() // 🔹 чтобы не крашилось при смене version
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
