package com.example.cheery.data
import androidx.room.*

@Entity(
    tableName = "user_table"
)
data class User (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val username: String,
    val password: String
)