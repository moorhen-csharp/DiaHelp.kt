package dev.moorhen.diahelp.view.fragments

import SugarEntryViewModel
import SugarEntryViewModelFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.data.repository.SugarRepository
import dev.moorhen.diahelp.utils.SessionManager // ✅

class SugarEntryFragment : Fragment() {

    private lateinit var viewModel: SugarEntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sugar_entry, container, false)

        val repository = SugarRepository(requireContext())
        // ✅ Создаем SessionManager
        val session = SessionManager(requireContext())
        // ✅ Создаем фабрику с repository, application и sessionManager
        val factory = SugarEntryViewModelFactory(repository, requireActivity().application, session)
        // ✅ Используем обновленную фабрику
        viewModel = ViewModelProvider(this, factory)[SugarEntryViewModel::class.java]

        val sugarInput = view.findViewById<TextInputEditText>(R.id.inputSugarLevel)
        val insulinInput = view.findViewById<TextInputEditText>(R.id.inputInsulin)
        val sugarTypeGroup = view.findViewById<com.google.android.material.chip.ChipGroup>(R.id.chipSugarType)
        val healthGroup = view.findViewById<com.google.android.material.chip.ChipGroup>(R.id.chipHealth)
        val btnNotMeasured = view.findViewById<MaterialButton>(R.id.btnNotMeasured)
        val btnSave = view.findViewById<MaterialButton>(R.id.btnSave)

        sugarTypeGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            viewModel.selectedSugarType = chip?.text?.toString()
        }

        healthGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            viewModel.selectedHealthType = chip?.text?.toString()
        }

        btnNotMeasured.setOnClickListener {
            viewModel.notMeasured()
            sugarInput.setText("0")
        }

        btnSave.setOnClickListener {
            viewModel.sugarLevel = sugarInput.text.toString().toDoubleOrNull()
            viewModel.insulinDose = insulinInput.text.toString().toDoubleOrNull()

            val success = viewModel.saveNote()
            if (success) parentFragmentManager.popBackStack()
        }

        return view
    }
}