package dev.moorhen.diahelp.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        val noDataText = view.findViewById<TextView>(R.id.tvNoData)


        // 🔹 Инициализация ViewModel
        val repository = SugarRepository(requireContext())
        val factory = SugarNoteViewModelFactory(repository, requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[SugarNoteViewModel::class.java]

        viewModel.isEmpty.observe(viewLifecycleOwner) { isEmpty ->
            noDataText.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }

        // 🔹 Настраиваем RecyclerView
        adapter = SugarAdapter(emptyList())
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // 🔹 Настраиваем выпадающий список периодов
        val periods = listOf("1 День", "1 Неделя", "1 Месяц", "3 Месяца", "6 Месяцев", "1 Год")
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

            // загружаем кастомный layout диалога
            val dialogView = layoutInflater.inflate(R.layout.dialog_clear_list, null)

            // создаём диалог
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()

            // ➤ ДЕЛАЕТ СКРУГЛЁННЫЕ КРАЯ В ЛЮБОЙ ТЕМЕ
            dialog.window?.setBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_dialog_containers)
            )

            dialog.show()

            // кнопки диалога
            val btnOk = dialogView.findViewById<Button>(R.id.btnOk)
            val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

            btnOk.setOnClickListener {
                viewModel.clearNotes()
                dialog.dismiss()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
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
    private fun showClearConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_clear_list, null)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        btnOk.setOnClickListener {
            viewModel.clearNotes()
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun onResume() {
        super.onResume()
        // 🔹 Обновляем список и среднее после возвращения с экрана ввода
        viewModel.loadSugarNotes()
        viewModel.calculateAverage()
    }
}
