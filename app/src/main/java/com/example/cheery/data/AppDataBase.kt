package com.example.cheery.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDataBase: RoomDatabase(){
    abstract fun userDao(): UserDao
}
object DatabaseClient{
    @Volatile
    private var INSTANCE: AppDataBase?=null
    fun getDatabase(context: Context): AppDataBase{
        return INSTANCE?:synchronized(this){
            val instance=Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "user_database"
            ).build()
            INSTANCE=instance
            instance
        }
    }

}