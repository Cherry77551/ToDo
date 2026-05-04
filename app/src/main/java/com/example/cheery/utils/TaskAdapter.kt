package com.example.cheery.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cheery.R
import com.example.cheery.data.Task
import java.text.SimpleDateFormat
import java.util.Date
import android.graphics.Paint

class TaskAdapter(val list: MutableList<Task>): RecyclerView.Adapter<TaskAdapter.ViewHolder>(){
    var onItemClick: ((Int) -> Unit)? = null
    var onCheckChange: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.id.rvTask, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = list[position]
        holder.title.text = task.title
        holder.isCompleted.isChecked = task.isCompleted
        val dateFormat = SimpleDateFormat("MM-dd HH:mm", java.util.Locale.getDefault())
        holder.date.text = dateFormat.format(Date(task.date))
        holder.ivPin.visibility = if (task.isPinned) View.VISIBLE else View.GONE
        if (task.isCompleted) {
            holder.title.paint.isStrikeThruText = true  // 加删除线
            holder.title.alpha = 0.5f                   // 变灰
        } else {
            holder.title.paint.isStrikeThruText = false
            holder.title.alpha = 1f
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val isCompleted: CheckBox = itemView.findViewById(R.id.cbComp)
        val ivPin: TextView = itemView.findViewById(R.id.ivPin)
        val date = view.findViewById<TextView>(R.id.tvDate)
        init {
            view.setOnClickListener {
                val pos=adapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    onItemClick?.invoke(pos)
                }
            }
            isCompleted.setOnCheckedChangeListener { _,isChecked ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onCheckChange?.invoke(pos)
                }
            }
        }
    }
}
