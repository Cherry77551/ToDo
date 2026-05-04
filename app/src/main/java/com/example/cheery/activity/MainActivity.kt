package com.example.cheery.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cheery.R
import com.example.cheery.data.DatabaseClient
import com.example.cheery.data.Task
import com.example.cheery.databinding.ActivityMainBinding
import com.example.cheery.repository.TaskRepository
import com.example.cheery.repository.UserRepository
import com.example.cheery.utils.DeleteCallBack
import com.example.cheery.utils.TaskAdapter
import com.example.cheery.viewmodel.TaskViewmodel
import com.example.cheery.viewmodel.UserViewmodel

class MainActivity : AppCompatActivity() {
    lateinit var adapter: TaskAdapter
    lateinit var viewmodel: TaskViewmodel
    private lateinit var binding: ActivityMainBinding
    val tasks = mutableListOf<Task>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity","onCreate")
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
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
        val username=intent.getStringExtra("username").toString()
        //退出按钮
        binding.btnExit.setOnClickListener { view ->
            val menu= PopupMenu(this,view)
            menu.inflate(R.menu.main)
            menu.setOnMenuItemClickListener { item ->
                (if(item.itemId== R.id.logout) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    true
                }else if(item.itemId==R.id.delete){
                val userDao= DatabaseClient.getDatabase(this).userDao()
                val userRepo= UserRepository(userDao)
                val model= UserViewmodel(userRepo)
                model.delete(username)
                }
                else false
                        )as Boolean
            }
            menu.show()
        }
        //初始化rv
        initRv();
        //设置侧滑
        swipe()
        //更新列表
        viewmodel.allTasks.observe(this) { newTasks ->
            tasks.clear()
            tasks.addAll(newTasks)
            adapter?.notifyDataSetChanged()
        }
    }
    fun initRv(){
        adapter= TaskAdapter(tasks)
        adapter.onItemClick = { position ->
            val task = tasks[position]
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("task_title", task.title)
            intent.putExtra("task_detail", task.detail)
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
        val callback= DeleteCallBack(
            onDelete = {position ->
            val dialogView= LayoutInflater.from(this).inflate(R.layout.dialog,null)
            val dialog= AlertDialog.Builder(this).setView(dialogView).setCancelable(true).create()
            dialogView.findViewById<Button>(R.id.cancel).setOnClickListener{
                dialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.delete).setOnClickListener {
                viewmodel.deleteTask(tasks[position])
                dialog.dismiss()
            }
                dialog.show()
        },
            onPin = {position ->
                viewmodel.togglePin(tasks[position])
            })
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvItem)
    }
}