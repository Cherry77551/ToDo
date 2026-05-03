package com.example.cheery.repository

import com.example.cheery.data.User
import com.example.cheery.data.UserDao

class UserRepository(private val userDao: UserDao) {
    //注册
    suspend fun register(username: String, password: String): Boolean {
        val user = userDao.byName(username)
        if (user != null)
            return false//用户名已存在就返回false
        val newUser = User(username = username, password = password)
        userDao.insertUser(newUser)
        return true
    }

    //登录
    suspend fun login(username: String, password: String): User? {
        val user = userDao.byName(username)
        if (user != null && user.password == password) {
            return user
        }
        return null
    }

    //注销
    suspend fun delete(username: String): Boolean {
        val user = userDao.byName(username)
        if (user != null) {
            userDao.deleteUser(user)
        }
        return true
    }
}