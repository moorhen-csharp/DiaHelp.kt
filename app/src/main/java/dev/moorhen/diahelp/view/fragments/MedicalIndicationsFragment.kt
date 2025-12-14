package dev.moorhen.diahelp.view.fragments

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.utils.showSuccessToast

class MedicalIndicationsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_medical_indications, container, false)

        val bloodAnalysis = view.findViewById<ConstraintLayout>(R.id.bloodAnalysis)

        bloodAnalysis.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MedicalAnalysisFragment())
                .addToBackStack(null)
                .commit()
        }

        return  view
    }
}