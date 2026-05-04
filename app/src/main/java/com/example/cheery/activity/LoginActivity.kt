package com.example.cheery.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cheery.R
import com.example.cheery.data.DatabaseClient
import com.example.cheery.data.UserDao
import com.example.cheery.databinding.ActivityLoginBinding
import com.example.cheery.repository.UserRepository
import com.example.cheery.utils.PreferenceManage
import com.example.cheery.viewmodel.UserViewmodel

class LoginActivity : AppCompatActivity() {
    private lateinit var userDao: UserDao
    private lateinit var viewmodel: UserViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userDao = DatabaseClient.getDatabase(this).userDao()
        viewmodel= UserViewmodel(UserRepository(userDao))
        //点击登录
        binding.btnLogin.setOnClickListener {
            val username = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val isRemember=binding.cbRemember.isChecked
            viewmodel.login(username, password)
            PreferenceManage.saveLoginInfo(this,username,password,isRemember)
        }
        // 观察登录结果
        viewmodel.loginResult.observe(this) { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            if (result.contains("成功")) {
                // 登录成功，跳转到主页
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("username",binding.etName.text.toString())
                startActivity(intent)
                finish()
            }
        }
        //点击文字去注册
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        //记住密码
        if(PreferenceManage.isRememberPassword(this)){
            binding.etName.setText(PreferenceManage.getSavedUsername(this))
            binding.etPassword.setText(PreferenceManage.getSavedPassword(this))
            binding.cbRemember.isChecked=true
        }
    }
}