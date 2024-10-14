package com.app.simplemoney.Models.Room

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.motus4.Models.Room.DaoMonthlyExpense
import com.app.motus4.Models.Room.DaoPayment
import com.app.simplemoney8.Models.Room.DaoExpense
import com.app.simplemoney8.Models.Room.DaoLanguage

val MIGRATION_15_16 = object : Migration(15, 16) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS payment (\n" +
                "    id INTEGER PRIMARY KEY NOT NULL,  -- id será a chave primária e terá um valor fixo\n" +
                "    payment REAL                      -- payment é um valor do tipo REAL (Double)\n" +
                ");\n")

    }
}

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database" )
                .addMigrations(MIGRATION_15_16)
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
