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
import dev.moorhen.diahelp.viewmodel.CorrectionViewModel
import dev.moorhen.diahelp.utils.showIncorrectToast

class CorrectionFragment : Fragment() {
    private val viewModel: CorrectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_correction, container, false)

        val currentGlucose = view.findViewById<EditText>(R.id.current_glucose)
        val targetGlucose = view.findViewById<EditText>(R.id.target_glucose)
        val calculateButton = view.findViewById<Button>(R.id.result)
        val correctionInsulin = view.findViewById<TextView>(R.id.correction_insulin)

        currentGlucose.setText("0")

        currentGlucose.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && currentGlucose.text.toString() == "0") {
                currentGlucose.text.clear()
            } else if (!hasFocus && currentGlucose.text.isNullOrEmpty()) {
                currentGlucose.setText("0")
            }
        }

        correctionInsulin.setText("0")
        targetGlucose.setText("5")

        // Кнопка "Рассчитать"
        calculateButton.setOnClickListener {
            val current = currentGlucose.text.toString().toDoubleOrNull()
            val target = targetGlucose.text.toString().toDoubleOrNull()

            if (current != null && target != null && current > 0 && target > 0) {
                viewModel.calculateInsulin(current, target)
            } else {
                Toast(requireContext()).showIncorrectToast("Некорректное значение!", requireActivity())
            }
        }

        // Наблюдение за результатом расчёта
        viewModel.correctionResult.observe(viewLifecycleOwner) { result ->
            correctionInsulin.text = result
        }

        return view
    }
}
