package com.example.todolistwithsharedpreference

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistwithsharedpreference.Adapter.TaskAdapter
import com.example.todolistwithsharedpreference.Data.Task

class MainActivity : AppCompatActivity() {
    private lateinit var tasklist: MutableList<Task>
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editTaskEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("tasks", Context.MODE_PRIVATE)

        recyclerView = findViewById(R.id.recyclerview)
        editTaskEditText = findViewById(R.id.edittaskET)
        tasklist = retriveTask()



        val saveButton: Button = findViewById(R.id.savebtn)

        saveButton.setOnClickListener {
            val taskText = editTaskEditText.text.toString()
            if (taskText.isNotEmpty()) {
                val task=Task(taskText,false)
                tasklist.add(task)
                saveTask(tasklist)
                taskAdapter.notifyItemInserted(tasklist.size-1)
                editTaskEditText.text.clear()

            }
            else{
                Toast.makeText(this, "Task title cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        taskAdapter= TaskAdapter(tasklist,object :TaskAdapter.TaskClickListener{
            override fun onEditClick(position: Int) {
                editTaskEditText.setText(tasklist[position].title)
                tasklist.removeAt(position)
                taskAdapter.notifyDataSetChanged()
            }

            override fun onDeleteClick(position: Int) {
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Delete Task")
                alertDialog.setMessage("are you sure you want to delete this task?")
                alertDialog.setPositiveButton("Yes") { _, _ ->
                    deleteTask(position)
                }
                alertDialog.setNegativeButton("No") { _, _ -> }
                alertDialog.show()
            }
        })
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun saveTask(tasklist: MutableList<Task>) {
        val editor = sharedPreferences.edit()
        val taskSet= hashSetOf<String>()

        tasklist.forEach{taskSet.add(it.title)}
        editor.putStringSet("tasks",taskSet)
        editor.apply()
    }

    private fun deleteTask(position: Int) {
        tasklist.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
        saveTask(tasklist)
    }

    private fun retriveTask(): MutableList<Task> {
        val tasks = sharedPreferences.getStringSet("tasks",HashSet())?:HashSet()
        return tasks.map { Task(it, false) }.toMutableList()
    }
}