package dev.moorhen.diahelp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val username: String,             // Логин
    val email: String,                // Почта
    val password: String,             // Пароль
    val coeffInsulin: Double = 0.0,   // Коррекционный инсулин
    val registrationDate: Date = Date() // Дата регистрации
)
