package com.app.simplemoney.Models.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.motus4.Models.Room.DaoMonthlyExpense
import com.app.motus4.Models.Room.DataClass.ModelsLanguage
import com.app.motus4.Models.Room.DataClass.MonthlyExpense
import com.app.simplemoney6.Models.Room.DataClass.Expense
import com.app.simplemoney8.Models.Room.DaoExpense
import com.app.simplemoney8.Models.Room.DaoLanguage

@Database(entities = [Bank::class, Expense::class, ModelsLanguage::class, MonthlyExpense::class], version = 15)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : Dao
    abstract fun userDaoExpense() : DaoExpense
    abstract fun userDaoLanguage() : DaoLanguage
    abstract fun userDaoMonthly() : DaoMonthlyExpense
}
