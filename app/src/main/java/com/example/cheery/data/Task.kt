package com.example.cheery.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks"
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    val title: String,
    val detail: String?,
    val isCompleted: Boolean=false,
    val isPinned: Boolean=false,
    val date:Long = System.currentTimeMillis()
)
