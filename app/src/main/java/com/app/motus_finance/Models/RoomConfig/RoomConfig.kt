package com.app.motus_finance.Models.RoomConfig

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.motus_finance.Models.DAO.BankDao
import com.app.motus_finance.Models.DAO.DueDatesDAO
import com.app.motus_finance.Models.DAO.ExpensesDAO
import com.app.motus_finance.Models.DAO.GraphicsDAO
import com.app.motus_finance.Models.DAO.PaymentDAO
import com.app.motus_finance.Models.Entities.Banks
import com.app.motus_finance.Models.Entities.DueDates
import com.app.motus_finance.Models.Entities.Expenses
import com.app.motus_finance.Models.Entities.Graphics
import com.app.motus_finance.Models.Entities.Payments

@Database(entities = [Banks::class, Expenses::class, Payments::class, DueDates::class, Graphics::class], version = 3)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bankDao() : BankDao
    abstract fun expenseDao() : ExpensesDAO
    abstract fun paymentsDao() : PaymentDAO
    abstract fun dueDateDao() : DueDatesDAO
    abstract fun graphicsDao() : GraphicsDAO
}
