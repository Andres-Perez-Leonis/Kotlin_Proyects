package com.example.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (private var taskList: MutableList<Task>, private val context: Context, private val onItemClick: (Int) -> Unit):
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    // En este ejemplo cada elemento consta solo de un nombre
    inner class ViewHolder(var cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        val radioButton: RadioButton = cardView.findViewById(R.id.btn_task)
        init {
            radioButton.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }

    // El layout manager invoca este método para renderizar cada elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.element_list, parent, false) as CardView

        // Aquí podemos definir tamaños, márgenes, paddings, etc
        return ViewHolder(v)
    }

    fun addTask(task: Task) {
        taskList.add(task)
        notifyItemInserted(taskList.size - 1)
    }

    // Este método asigna valores para cada elemento de la lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardView.findViewById<TextView>(R.id.name_task).text = taskList[position].getNoum()
        holder.cardView.findViewById<TextView>(R.id.date).text = taskList[position].getDate().toString()
        when(taskList[position].getPriority()) {
            1 -> holder.cardView.findViewById<RadioButton>(R.id.btn_task).buttonTintList =
                ContextCompat.getColorStateList(context, R.color.red)
            2 -> holder.cardView.findViewById<RadioButton>(R.id.btn_task).buttonTintList =
                ContextCompat.getColorStateList(context, R.color.orange)
            3 -> holder.cardView.findViewById<RadioButton>(R.id.btn_task).buttonTintList =
                ContextCompat.getColorStateList(context, R.color.yellow)
            4 -> holder.cardView.findViewById<RadioButton>(R.id.btn_task).buttonTintList =
                ContextCompat.getColorStateList(context, R.color.green)
        }
    }


    // Cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejm, si implementamos filtros o búsquedas)
    override fun getItemCount() = taskList.size
}