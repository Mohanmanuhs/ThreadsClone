package com.example.threadsclone.util

import android.content.Context

object SharedPref {
    fun saveData(name:String, email: String,userName:String, context: Context, bio:String, imageUrl:String){
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("userName", userName)
        editor.putString("bio", bio)
        editor.putString("imageUrl", imageUrl)
        editor.apply()

    }
    fun getName(context: Context):String {
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPref.getString("name","")!!
    }
    fun getEmail(context: Context):String {
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPref.getString("email","")!!
    }
    fun getUserName(context: Context):String {
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPref.getString("userName","")!!
    }
    fun getBio(context: Context):String {
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPref.getString("bio","")!!
    }
    fun getImageUrl(context: Context):String {
        val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPref.getString("imageUrl","")!!
    }
}