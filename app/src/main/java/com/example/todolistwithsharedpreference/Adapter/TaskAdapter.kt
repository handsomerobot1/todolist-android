package com.example.todolistwithsharedpreference.Adapter

import android.content.DialogInterface.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistwithsharedpreference.Data.Task
import com.example.todolistwithsharedpreference.databinding.ListItemBinding

class TaskAdapter(private val tasklist:MutableList<Task>,private val clicklisten:TaskClickListener):
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    interface TaskClickListener {
    fun onEditClick(position: Int)
    fun onDeleteClick(position: Int)
}

    class TaskViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskTittle.text = task.title
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
