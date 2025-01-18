package com.app.motus_finance.Models.RoomConfig

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.motus_finance.Models.DAO.BankDao
import com.app.motus_finance.Models.DAO.ExpensesDAO
import com.app.motus_finance.Models.DAO.PaymentDAO

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {}
}

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database" )
                .build()

            INSTANCE = instance
            instance
        }
    }

    fun getBankDAO(context: Context): BankDao {
        return getDatabase(context).bankDao()
    }

    fun getExpenseDAO(context: Context): ExpensesDAO {
        return getDatabase(context).expenseDao()
    }

    fun getPaymentDAO(context: Context): PaymentDAO {
        return getDatabase(context).paymentsDao()
    }
}
