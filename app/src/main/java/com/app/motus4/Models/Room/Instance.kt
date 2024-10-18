package com.app.simplemoney.Models.Room

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.motus4.Models.Room.DaoMonthlyExpense
import com.app.motus4.Models.Room.DaoPayment
import com.app.simplemoney8.Models.Room.DaoExpense
import com.app.simplemoney8.Models.Room.DaoLanguage

val MIGRATION_17_18 = object : Migration(17, 18) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            CREATE TABLE payment_new (
                id INTEGER PRIMARY KEY NOT NULL,
                payment REAL
            )
        """.trimIndent())

        // Copie os dados da tabela antiga para a nova
        database.execSQL("""
            INSERT INTO payment_new (id, payment)
            SELECT id, payment FROM payment
        """.trimIndent())

        // Apague a tabela antiga
        database.execSQL("DROP TABLE payment")

        // Renomeie a nova tabela para o nome antigo
        database.execSQL("ALTER TABLE payment_new RENAME TO payment")

    }
}

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database" )
                .addMigrations(MIGRATION_17_18)
                .build()

            INSTANCE = instance
            instance
        }
    }

    fun getDao(context: Context): Dao {
        return getDatabase(context).userDao()
    }
    fun getDaoExpense(context: Context): DaoExpense {
        return getDatabase(context).userDaoExpense()
    }
    fun getDaoLanguage(context: Context) : DaoLanguage{
        return getDatabase(context).userDaoLanguage()
    }
    fun getDaoMonthly(context: Context) : DaoMonthlyExpense{
        return getDatabase(context).userDaoMonthly()
    }
    fun getDaoPayment(context: Context) : DaoPayment{
        return getDatabase(context).userDaoPayment()
    }

}
