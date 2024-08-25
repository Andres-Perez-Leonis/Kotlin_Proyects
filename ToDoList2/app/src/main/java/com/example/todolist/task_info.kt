package com.example.todolist

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

class task_info : AppCompatActivity() {

    private lateinit var noum_task: EditText
    private lateinit var date_task: TextView
    private lateinit var priority_task: AutoCompleteTextView
    private lateinit var description_task: EditText
    private lateinit var save_task_btn: Button
    private lateinit var noum: String
    private var date: LocalDate = LocalDate.now()
    private var priority = 4
    private lateinit var description: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContentView(R.layout.activity_task_info)

        noum_task = findViewById(R.id.task_noum)
        date_task = findViewById(R.id.task_date)
        priority_task = findViewById(R.id.priority)
        description_task = findViewById(R.id.task_description)
        save_task_btn = findViewById(R.id.add_task)



        val priorities = resources.getStringArray(R.array.priority_options)
        val adapter = ArrayAdapter(this, R.layout.list_priority_item, priorities)
        priority_task.setAdapter(adapter)

        priority_task.setOnItemClickListener { parent, view, position, id ->
            priority = position
        }

        val closeBtn: ImageButton = findViewById(R.id.close_btn)
        closeBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        date_task.text = formatDate(date.monthValue, date.dayOfMonth)
        date_task.setOnClickListener(showCalendar)

        save_task_btn.setOnClickListener {
            noum = noum_task.text.toString()
            description = description_task.text.toString()
            val probe: Boolean = noum.isNotEmpty() && description.isNotEmpty()
            if(!probe) return@setOnClickListener
            val newTask = Task(noum, description, date, priority)
            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent)
            Log.d("taskInfo", "Task devuelto: $newTask")
            returnIntent.putExtra("task", newTask)
            setResult(RESULT_OK, returnIntent)
            finish()
            //val parce: Parcelable = Parcels.wrap(new_task)
        }
    }

    private val showCalendar = OnClickListener {
        val today: LocalDate = LocalDate.now()
        val dialog: DatePickerDialog = DatePickerDialog(this, setDateListener, today.year, today.monthValue, today.dayOfMonth)
        dialog.show()
    }

    private val setDateListener = DatePickerDialog.OnDateSetListener  {_, year, month, day ->
        val dateText: String = formatDate(month, day)
        date_task.text = dateText
        date = LocalDate.of(year, month, day)
    }




    private fun formatDate(month: Int, day: Int): String {
        return "$day ${Month.of(month + 1).getDisplayName(TextStyle.SHORT, Locale("es", "ES"))}"
    }


}