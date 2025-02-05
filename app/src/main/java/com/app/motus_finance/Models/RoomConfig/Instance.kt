package com.app.motus_finance.Models.RoomConfig

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.motus_finance.Models.DAO.DueDatesDAO
import com.app.motus_finance.Models.DAO.ExpensesDAO
import com.app.motus_finance.Models.DAO.GraphicsDAO
import com.app.motus_finance.Models.DAO.MarketDAO
import com.app.motus_finance.Models.DAO.PaymentDAO

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            CREATE TABLE new_table_expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                bankId INTEGER,
                expenseDescription TEXT,
                value REAL,
                spentOrReceived TEXT,
                fixedOrVariable TEXT,
                date TEXT,
                dueDate TEXT,
                classification TEXT,
                readyForDeletion INTEGER NOT NULL,
                FOREIGN KEY(bankId) REFERENCES market_entity(id) ON DELETE CASCADE
            )
        """)

        // Copiar os dados da tabela antiga para a nova
        database.execSQL("""
            INSERT INTO new_table_expenses (id, bankId, expenseDescription, value, spentOrReceived, fixedOrVariable, date, dueDate, classification, readyForDeletion)
            SELECT id, bankId, expenseDescription, value, spentOrReceived, fixedOrVariable, date, dueDate, classification, readyForDeletion FROM table_expenses
        """)

        // Excluir a tabela antiga
        database.execSQL("DROP TABLE table_expenses")

        // Renomear a nova tabela para manter o nome correto
        database.execSQL("ALTER TABLE new_table_expenses RENAME TO table_expenses")

    }
}


object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database" )
                .addMigrations(MIGRATION_4_5)

                .build()

            INSTANCE = instance
            instance
        }
    }

    fun getBankDAO(context: Context): MarketDAO {
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
