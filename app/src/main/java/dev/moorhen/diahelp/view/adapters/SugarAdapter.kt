package dev.moorhen.diahelp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.data.model.SugarModel
import java.text.SimpleDateFormat
import java.util.*

class SugarAdapter(private var items: List<SugarModel>) :
    RecyclerView.Adapter<SugarAdapter.SugarViewHolder>() {

    inner class SugarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDate: TextView = itemView.findViewById(R.id.textDate)
        val textSugar: TextView = itemView.findViewById(R.id.textSugar)
        val textInsulin: TextView = itemView.findViewById(R.id.textInsulin)
        val textType: TextView = itemView.findViewById(R.id.textType)
        val textHealth: TextView = itemView.findViewById(R.id.textHealth)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SugarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sugar_note, parent, false)
        return SugarViewHolder(view)
    }

    override fun onBindViewHolder(holder: SugarViewHolder, position: Int) {
        val item = items[position]
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        holder.textDate.text = formatter.format(item.Date)
        holder.textSugar.text =
            if (item.SugarLevel == -1.0) "Сахар: не измерял" else "Сахар: ${item.SugarLevel} ммоль/л"
        holder.textInsulin.text =
            if (item.InsulinDose == -1.0) "Инсулин: не вводил" else "Инсулин: ${item.InsulinDose} ед."
        holder.textType.text = item.MeasurementTime
        holder.textHealth.text = item.HealthType
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newList: List<SugarModel>) {
        items = newList
        notifyDataSetChanged()
    }
}
