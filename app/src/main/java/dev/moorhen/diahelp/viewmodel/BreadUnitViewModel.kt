package dev.moorhen.diahelp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BreadUnitViewModel(application: Application) : AndroidViewModel(application) {

    private val _calcResult = MutableLiveData<String>()
    val calcResult: LiveData<String> = _calcResult

    fun calculateBU(carbohydrates: Double, productWeight: Double){
        viewModelScope.launch {

            if (carbohydrates <= 0 || productWeight <= 0) {
                return@launch
            }

            val calcBU = ((carbohydrates * productWeight / 100) / 12)
            _calcResult.postValue("%.2f".format(calcBU))
        }
    }
}