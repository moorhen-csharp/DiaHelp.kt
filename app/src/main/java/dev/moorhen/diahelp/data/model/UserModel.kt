package dev.moorhen.diahelp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val name: String? = null,
    val lastName: String? = null,
    val coeffInsulin: Double = 0.0,
    val email: String,
    val experience: String? = null,
    val registrationDate: Date = Date()
)
