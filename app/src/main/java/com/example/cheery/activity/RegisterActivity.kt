package com.example.cheery.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cheery.R
import com.example.cheery.data.DatabaseClient
import com.example.cheery.data.UserDao
import com.example.cheery.databinding.ActivityRegisterBinding
import com.example.cheery.repository.UserRepository
import com.example.cheery.viewmodel.UserViewmodel

class RegisterActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    private lateinit var viewmodel: UserViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userDao = DatabaseClient.getDatabase(this).userDao()
        viewmodel= UserViewmodel(UserRepository(userDao))
        //点击注册
        binding.btnRegister.setOnClickListener {
            val username = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            viewmodel.register(username, password)
        }
        //观察注册结果
        viewmodel.registerResult.observe(this){result->
            Toast.makeText(this,result,Toast.LENGTH_SHORT).show()
            if(result.contains("欢迎")){
                startActivity(Intent(this, MainActivity::class.java))
                finish()}
        }
    }
}