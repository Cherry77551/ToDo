package com.example.cheery.repository

import com.example.cheery.data.Task
import com.example.cheery.data.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    //全部任务
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
    //添加
    suspend fun addTask(task: Task) = taskDao.insertTask(task)
    //更新
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    //删除
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    //切换完成状态
    suspend fun toggleComplete(taskId: Int, isCompleted: Boolean) {
        taskDao.toggleComplete(taskId, isCompleted)
    }
    //切换置顶状态
    suspend fun togglePin(taskId: Int, isPinned: Boolean) {
        taskDao.togglePin(taskId, isPinned)
    }
}