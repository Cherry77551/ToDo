package com.example.cheery.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task:Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
    //通过id获得任务
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): Task?

    // 获取所有任务（按置顶优先，再按时间倒序）
    @Query("SELECT * FROM tasks ORDER BY isPinned DESC, date DESC")
    fun getAllTasks(): Flow<List<Task>>

    // 切换完成状态
    @Query("UPDATE tasks SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun toggleComplete(taskId: Long, isCompleted: Boolean)

    // 置顶
    @Query("UPDATE tasks SET isPinned = :isPinned WHERE id = :taskId")
    suspend fun togglePin(taskId: Long, isPinned: Boolean)
}