package dev.moorhen.diahelp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.data.repository.SugarRepository
import dev.moorhen.diahelp.view.adapters.SugarAdapter
import dev.moorhen.diahelp.viewmodel.SugarNoteViewModel
import dev.moorhen.diahelp.viewmodel.SugarNoteViewModelFactory

class SugarNoteFragment : Fragment() {

    private lateinit var viewModel: SugarNoteViewModel
    private lateinit var adapter: SugarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sugarnote, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerReadings)
        val btnAddData = view.findViewById<MaterialButton>(R.id.btnAddData)
        val btnClear = view.findViewById<MaterialButton>(R.id.btnClear)

        // ✅ инициализация
        val repository = SugarRepository(requireContext())
        val factory = SugarNoteViewModelFactory(repository, requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[SugarNoteViewModel::class.java]

        adapter = SugarAdapter(emptyList())
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // ✅ слушаем обновления списка
        viewModel.sugarNotes.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        // ✅ кнопка очистить
        btnClear.setOnClickListener {
            viewModel.clearNotes()
        }

        // ✅ переход к добавлению данных
        btnAddData.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SugarEntryFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadSugarNotes()
    }
}
