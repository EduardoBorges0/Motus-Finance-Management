package com.app.motus_finance.Models.RoomConfig

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.motus_finance.Models.DAO.DueDatesDAO
import com.app.motus_finance.Models.DAO.ExpensesDAO
import com.app.motus_finance.Models.DAO.GraphicsDAO
import com.app.motus_finance.Models.DAO.MarketDAO
import com.app.motus_finance.Models.DAO.PaymentDAO
import com.app.motus_finance.Models.Entities.DueDates
import com.app.motus_finance.Models.Entities.Expenses
import com.app.motus_finance.Models.Entities.Graphics
import com.app.motus_finance.Models.Entities.Market
import com.app.motus_finance.Models.Entities.Payments

@Database(entities = [Market::class, Expenses::class, Payments::class, DueDates::class, Graphics::class], version = 5, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bankDao() : MarketDAO
    abstract fun expenseDao() : ExpensesDAO
    abstract fun paymentsDao() : PaymentDAO
    abstract fun dueDateDao() : DueDatesDAO
    abstract fun graphicsDao() : GraphicsDAO
}
