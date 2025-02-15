package com.example.todolistwithsharedpreference.Adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
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
            binding.classtitle.text = task.title
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
       val binding= ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tasklist.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task=tasklist[position]
        holder.bind(task)
        holder.binding.editbtn.setOnClickListener {
            clicklisten.onEditClick(position)
        }
        holder.binding.deletebtn.setOnClickListener {
            clicklisten.onDeleteClick(position)
        }
        holder.binding.checkbox.isChecked=task.isCompleted
       holder.binding.checkbox.setOnCheckedChangeListener{_,isChecked->
           tasklist[position].isCompleted=isChecked
       }
    }
}
