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

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 1. Crie uma nova tabela com o tipo correto para a coluna `img`
        database.execSQL(
            """
            CREATE TABLE banks_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT,
                color TEXT,
                img INTEGER, -- Alterado para INTEGER
                balance REAL,
                colorSpentsOrReceived TEXT,
                date TEXT,
                sum REAL
            )
            """.trimIndent()
        )

        // 2. Copie os dados da tabela antiga para a nova
        database.execSQL(
            """
            INSERT INTO banks_new (id, name, color, img, balance, colorSpentsOrReceived, date, sum)
            SELECT id, name, color, NULL, balance, colorSpentsOrReceived, date, sum
            FROM bank_entity
            """.trimIndent()
        )

        // 3. Exclua a tabela antiga
        database.execSQL("DROP TABLE bank_entity")

        // 4. Renomeie a nova tabela para o nome original
        database.execSQL("ALTER TABLE banks_new RENAME TO bank_entity")
    }
}


object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database" )
                .addMigrations(MIGRATION_1_2)
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
