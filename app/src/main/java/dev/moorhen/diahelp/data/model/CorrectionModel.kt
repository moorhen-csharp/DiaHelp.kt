package dev.moorhen.diahelp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "correction")
data class CorrectionModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val currentGlucose: Double,
    val targetGlucose: Double,
    val correctionInsulin: Double,
    val Date: Date = Date()
)