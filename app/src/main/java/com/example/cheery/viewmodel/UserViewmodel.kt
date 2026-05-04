package com.example.cheery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheery.data.User
import com.example.cheery.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewmodel(private val repo: UserRepository) : ViewModel(){
    //注册
    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> = _registerResult
    fun register(username: String, password: String) {
        if (username.trim().isEmpty()) {
            _registerResult.value = "用户名不能为空"
            return
        }
        if (password.trim().length < 6) {
            _registerResult.value = "密码长度不小于6位"
            return
        }
        viewModelScope.launch {
            repo.register(username, password)
            _registerResult.value = "欢迎来到ToDo！"
        }
    }

    //登录
    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult
    fun login(username: String, password: String) {
        if (username.trim().isEmpty()) {
            _loginResult.value = "用户名不能为空"
            return
        }
        if (password.trim().isEmpty()) {
            _loginResult.value = "密码不能为空"
            return
        }
        viewModelScope.launch {
            val user = repo.login(username, password)
            _loginResult.value = if (user != null) {
                "登录成功，欢迎回来 ${user.username}"
            } else {
                "登录失败：用户名或密码错误"
            }
        }
    }
    //注销
    fun delete(username: String) {
        viewModelScope.launch { repo.delete(username) }
    }
}