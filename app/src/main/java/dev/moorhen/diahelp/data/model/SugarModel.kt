package dev.moorhen.diahelp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "sugars")
data class SugarModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val SugarLevel: Double,
    val MeasurementTime: String,
    val HealthType: String,
    val InsulinDose: Int,
    val Date: Date = Date()
)
