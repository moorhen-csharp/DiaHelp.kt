package dev.moorhen.diahelp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.TextView
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

        // 🔹 UI элементы
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerReadings)
        val btnAddData = view.findViewById<ImageButton>(R.id.btnAddData)
        val btnClear = view.findViewById<MaterialButton>(R.id.btnClear)
        val dropdown = view.findViewById<AutoCompleteTextView>(R.id.periodDropdown)
        val avgText = view.findViewById<TextView>(R.id.textAverage)

        // 🔹 Инициализация ViewModel
        val repository = SugarRepository(requireContext())
        val factory = SugarNoteViewModelFactory(repository, requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[SugarNoteViewModel::class.java]

        // 🔹 Настраиваем RecyclerView
        adapter = SugarAdapter(emptyList())
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // 🔹 Настраиваем выпадающий список периодов
        val periods = listOf("1 День","Неделя", "3 Месяца", "6 Месяцев", "1 Год")
        val adapterDropdown = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, periods)
        dropdown.setAdapter(adapterDropdown)

        // При выборе периода пересчитываем средний сахар
        dropdown.setOnItemClickListener { _, _, position, _ ->
            val selected = periods[position]
            viewModel.selectedPeriod.value = selected
            viewModel.calculateAverage()
        }

        // 🔹 Наблюдение за списком записей
        viewModel.sugarNotes.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        // 🔹 Наблюдение за средним значением
        viewModel.average.observe(viewLifecycleOwner) { avg ->
            avgText.text = String.format("%.1f ммоль/л", avg)
        }

        // 🔹 Кнопка "Очистить"
        btnClear.setOnClickListener {
            viewModel.clearNotes()
        }

        // 🔹 Кнопка "Добавить данные"
        btnAddData.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SugarEntryFragment())
                .addToBackStack(null)
                .commit()
        }

        // Загружаем данные при создании
        viewModel.loadSugarNotes()
        viewModel.calculateAverage()

        return view
    }

    override fun onResume() {
        super.onResume()
        // 🔹 Обновляем список и среднее после возвращения с экрана ввода
        viewModel.loadSugarNotes()
        viewModel.calculateAverage()
    }
}
