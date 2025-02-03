package com.app.motus_finance.Models.RoomConfig

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.motus_finance.Models.DAO.BankDao
import com.app.motus_finance.Models.DAO.DueDatesDAO
import com.app.motus_finance.Models.DAO.ExpensesDAO
import com.app.motus_finance.Models.DAO.GraphicsDAO
import com.app.motus_finance.Models.DAO.PaymentDAO

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE graphics_entity ADD COLUMN highestSpendingRating TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE graphics_entity ADD COLUMN valueSpendingRating REAL NOT NULL DEFAULT 0.0")

    }
}


object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database" )
                .addMigrations(MIGRATION_2_3)
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
    fun getDueDatesDAO(context: Context): DueDatesDAO {
        return getDatabase(context).dueDateDao()
    }
    fun getGraphicsDAO(context: Context): GraphicsDAO {
        return getDatabase(context).graphicsDao()
    }

}
