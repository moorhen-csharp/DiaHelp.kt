package dev.moorhen.diahelp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CorrectionViewModel : ViewModel() {

    private val _correctionResult = MutableLiveData<Double>()
    val correctionResult: LiveData<Double> = _correctionResult

    // Коэффициент чувствительности к инсулину (например, 1 ед. снижает на 2 ммоль)
    private val insulinSensitivity = 2.0

    fun calculateCorrection(currentGlucose: Double, targetGlucose: Double) {
        val difference = currentGlucose - targetGlucose
        val correction = if (difference > 0) difference / insulinSensitivity else 0.0
        _correctionResult.value = correction
    }
}
