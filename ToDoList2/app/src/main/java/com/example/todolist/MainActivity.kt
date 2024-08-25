package com.example.todolist

import android.app.TaskInfo
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val REQUEST_CODE_TASK = 1001
    }

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var taskList: MutableList<Task>
    private lateinit var context: Context
    private lateinit var listTaskView: RecyclerView

    private lateinit var  taskResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolBar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolBar)


        taskList = mutableListOf<Task>()

        context = applicationContext


        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        listTaskView = findViewById<RecyclerView>(R.id.to_do_list)
        listTaskView.adapter = TaskAdapter(taskList, this) {
            position ->
                taskList.removeAt(position)
                listTaskView.adapter?.notifyItemRemoved(position - 1)
        }
        listTaskView.layoutManager = LinearLayoutManager(this)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        findViewById<ImageButton>(R.id.dropdown_menu).setOnClickListener {
            val popup = PopupMenu(this, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.actions, popup.menu)
            popup.show()
        }


        taskResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
                if (result.resultCode == RESULT_OK) {
                    //Toast.makeText(this, "Todo fue correctamente", Toast.LENGTH_SHORT).show()
                    val newTask: Task? = result.data?.getParcelableExtra("task")
                    if(newTask == null)
                        Toast.makeText(this, "Devolvio nulo", Toast.LENGTH_SHORT).show()
                    newTask?.let {
                        Toast.makeText(this, newTask.getNoum(), Toast.LENGTH_SHORT).show()
                        taskList.add(it)
                        listTaskView.adapter?.notifyItemInserted(taskList.size - 1)

                    }
                }
        }

        val addTaskBtn: FloatingActionButton = findViewById(R.id.add_task)
        addTaskBtn.setOnClickListener {
            val intent: Intent = Intent(this, task_info::class.java)
            taskResultLauncher.launch(intent)
            //startActivityForResult(intent, REQUEST_CODE_TASK)
        }

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.Item1 -> Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show()
            R.id.Item2 -> Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show()
            R.id.Item3 -> Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MainActivity", "Se llama a la funcion")
        Log.d("MainActivity", resultCode.toString())
        Toast.makeText(this, resultCode.toString(), Toast.LENGTH_SHORT).show()
        if (requestCode == resultCode && resultCode == RESULT_OK) {
            Toast.makeText(this, "Todo fue correctamente", Toast.LENGTH_SHORT).show()
            val newTask: Task? = data?.getParcelableExtra("task")
            newTask?.let {
                Toast.makeText(this, newTask.getNoum(), Toast.LENGTH_SHORT).show()
                taskList.add(it)
                listTaskView.adapter?.notifyItemInserted(taskList.size - 1)
            }
        }
    }


 */
}

