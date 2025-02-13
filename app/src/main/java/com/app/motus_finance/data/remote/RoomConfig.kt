package com.app.motus_finance.data.remote

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.motus_finance.data.dao.DueDatesDAO
import com.app.motus_finance.data.dao.ExpensesDAO
import com.app.motus_finance.data.dao.GraphicsDAO
import com.app.motus_finance.data.dao.MarketDAO
import com.app.motus_finance.data.dao.PaymentDAO
import com.app.motus_finance.data.models.DueDates
import com.app.motus_finance.data.models.Expenses
import com.app.motus_finance.data.models.Graphics
import com.app.motus_finance.data.models.Market
import com.app.motus_finance.data.models.Payments

@Database(entities = [Market::class, Expenses::class, Payments::class, DueDates::class, Graphics::class], version = 5, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bankDao() : MarketDAO
    abstract fun expenseDao() : ExpensesDAO
    abstract fun paymentsDao() : PaymentDAO
    abstract fun dueDateDao() : DueDatesDAO
    abstract fun graphicsDao() : GraphicsDAO
}
