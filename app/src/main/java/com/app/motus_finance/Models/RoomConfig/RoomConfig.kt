package com.app.motus_finance.Models.RoomConfig

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.motus_finance.Models.DAO.BankDao
import com.app.motus_finance.Models.Entities.Banks
import com.app.motus_finance.Models.Entities.Expenses

@Database(entities = [Banks::class, Expenses::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bankDao() : BankDao
}
