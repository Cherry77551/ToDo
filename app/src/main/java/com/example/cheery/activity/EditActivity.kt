package com.example.cheery.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.cheery.R
import com.example.cheery.data.DatabaseClient
import com.example.cheery.data.Task
import com.example.cheery.repository.TaskRepository
import com.example.cheery.viewmodel.TaskViewmodel
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var viewmodel: TaskViewmodel
    val dao= DatabaseClient.getDatabase(this).taskDao()
    private var taskId: Long = -1L
    private var oldTask: Task? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etTitle=findViewById<EditText>(R.id.etTitle)
        etContent=findViewById<EditText>(R.id.etDetail)
        val repo= TaskRepository(dao)
        viewmodel= TaskViewmodel(repo)
        //接收从主页面任务点击传来的数据
        taskId=intent.getLongExtra("task_id",-1L)
        val title=intent.getStringExtra("task_title")?:" "
        val detail=intent.getStringExtra("task_detail")?:""
        etTitle.setText(title)
        etContent.setText(detail)
        //返回
        findViewById<Button>(R.id.btnBack).setOnClickListener{
            onDestroy()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //退出时直接保存
        save()
    }

    private fun save(){
        val title=etTitle.text.toString().trim()
        val detail=etContent.text.toString()
        if(title.isEmpty()) return//没有标题就不保存
        //如果任务是新建的
        if(taskId==-1L){
            viewmodel.add(title,detail)
        }
        //不是新建的话,要从Room里面读取
        else{
            lifecycleScope.launch{
                oldTask=dao.getTaskById(taskId)
                oldTask?.let {
                    viewmodel.update(Task(taskId,title,detail, isCompleted = it.isCompleted, isPinned = it.isPinned)) }
            }
        }
    }
}