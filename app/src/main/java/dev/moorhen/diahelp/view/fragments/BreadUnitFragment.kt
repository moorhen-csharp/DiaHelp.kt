package dev.moorhen.diahelp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.utils.showIncorrectToast
import dev.moorhen.diahelp.viewmodel.BreadUnitViewModel
import kotlin.getValue

class BreadUnitFragment : Fragment() {

    private val viewModel: BreadUnitViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_breadunit, container, false)

        val carbohydrates = view.findViewById<EditText>(R.id.Carbohydrates)
        val productWeight = view.findViewById<EditText>(R.id.ProductWeight)
        val calculateButton = view.findViewById<Button>(R.id.resultBU)
        val currentValue = view.findViewById<TextView>(R.id.CurrentValue)

        carbohydrates.setText("0")
        carbohydrates.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && carbohydrates.text.toString() == "0") {
                carbohydrates.text.clear()
            } else if (!hasFocus && carbohydrates.text.isNullOrEmpty()) {
                carbohydrates.setText("0")
            }
        }

        productWeight.setText("0")
        productWeight.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && productWeight.text.toString() == "0") {
                productWeight.text.clear()
            } else if (!hasFocus && productWeight.text.isNullOrEmpty()) {
                productWeight.setText("0")
            }
        }

        currentValue.setText("0")

        calculateButton.setOnClickListener {
            val carbs = carbohydrates.text.toString().toDoubleOrNull()
            val weight = productWeight.text.toString().toDoubleOrNull()

            if (carbs == null || weight == null || carbs <= 0 || weight <= 0 ) {
                Toast(requireContext()).showIncorrectToast("Некорректное значение!", requireActivity())
                return@setOnClickListener
            }

            val result = ((carbs * weight) / 100) / 12

            currentValue.text = String.format("%.1f", result)

            carbohydrates.setText("0")
            productWeight.setText("0")
        }

        return view
    }
}