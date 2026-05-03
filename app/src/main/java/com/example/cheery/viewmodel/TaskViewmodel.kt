package com.example.cheery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheery.data.Task
import com.example.cheery.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewmodel(private val taskRepository: TaskRepository): ViewModel() {
    //全部任务
    private val _allTasks= MutableLiveData<List<Task>>()
    val allTasks: LiveData<List<Task>>get()=_allTasks
    // 提示消息
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>get() = _message
    //实时更新全部任务
    init {
        viewModelScope.launch {
            taskRepository.getAllTasks().collect{tasks-> _allTasks.value=tasks}
        }
    }
    //添加任务
    fun add(title: String,content: String){
        if (title.trim().isEmpty()) {
            _message.value = "标题不能为空"
            return
        }
        val task = Task(0,title, content)
        viewModelScope.launch {
            taskRepository.addTask(task)
            _message.value = "添加成功"
        }
    }
    // 删除任务
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
    // 切换完成状态
    fun toggleComplete(task: Task) {
        viewModelScope.launch {
            taskRepository.toggleComplete(task.id, !task.isCompleted)
        }
    }
    // 切换置顶
    fun togglePin(task: Task) {
        viewModelScope.launch {
            taskRepository.togglePin(task.id, !task.isPinned)
        }
    }
    }
