package com.example.cheery.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cheery.R
import com.example.cheery.data.DatabaseClient
import com.example.cheery.data.Task
import com.example.cheery.databinding.ActivityMainBinding
import com.example.cheery.repository.TaskRepository
import com.example.cheery.utils.DeleteCallBack
import com.example.cheery.utils.ItemTouchHelperAdapter
import com.example.cheery.utils.TaskAdapter
import com.example.cheery.viewmodel.TaskViewmodel

class MainActivity : AppCompatActivity() {
    lateinit var adapter: TaskAdapter
    lateinit var viewmodel: TaskViewmodel
    val binding= ActivityMainBinding.inflate(layoutInflater)
    val tasks = mutableListOf<Task>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dao= DatabaseClient.getDatabase(this).taskDao()
        val repo= TaskRepository(dao)
        viewmodel= TaskViewmodel(repo)
        //添加
        binding.btnAdd.setOnClickListener{
            startActivity(Intent(this, EditActivity::class.java))
        }
        //初始化rv
        initRv();
        //设置侧滑
        swipe()
    }
    fun initRv(){
        adapter= TaskAdapter(tasks)
        adapter.onItemClick = { position ->
            val task = tasks[position]
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("task_title", task.title)
            intent.putExtra("task_content", task.detail)
            intent.putExtra("task_id", task.id)
            startActivity(intent)
        }
        adapter.onCheckChange= { position ->
            val task=tasks[position]
            viewmodel.toggleComplete(task)
        }
        binding.rvItem.adapter=adapter
        binding.rvItem.layoutManager= LinearLayoutManager(this)
    }
    fun swipe(){
        val callback= DeleteCallBack(adapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvItem)
    }
}