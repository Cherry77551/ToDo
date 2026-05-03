package com.example.cheery.data
import androidx.room.*

@Dao
interface UserDao{
    //注册新用户
    @Insert
    suspend fun insertUser(user: User): Long
    //删除用户
    @Delete
    suspend fun deleteUser(user: User)
    //按用户名查找用户
    @Query("SELECT*FROM user_table WHERE username= :username")
    suspend fun byName(username: String): User?
}