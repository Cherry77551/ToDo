package com.example.cheery.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceManage {
     fun getPref(context: Context): SharedPreferences{
        return context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
    }
    // 保存登录信息
    fun saveLoginInfo(context: Context, username: String, password: String, isRemember: Boolean) {
        val editor = getPref(context).edit()
        if (isRemember) {
            editor.putString("username", username)
            editor.putString("password", password)
            editor.putBoolean("isRemember", true)
        } else {
            editor.clear()
        }
        editor.apply()
    }
    // 获取保存的用户名
    fun getSavedUsername(context: Context): String {
        return getPref(context).getString("username", "") ?: ""
    }

    // 获取保存的密码
    fun getSavedPassword(context: Context): String {
        return getPref(context).getString("password", "") ?: ""
    }

    // 是否记住密码
    fun isRememberPassword(context: Context): Boolean {
        return getPref(context).getBoolean("isRemember", false)
    }

}