package dev.moorhen.diahelp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "sugar_note")
data class SugarModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val SugarLevel: Double,
    val MeasurementTime: String,
    val HealthType: String,
    val InsulinDose: Double,
    val Date: Date = Date()
)
