package com.app.simplemoney.Models.Room

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.motus4.Models.Room.DaoMonthlyExpense
import com.app.simplemoney8.Models.Room.DaoExpense
import com.app.simplemoney8.Models.Room.DaoLanguage

val MIGRATION_13_14 = object : Migration(13, 14) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE table_expenses ADD COLUMN readyForDeletion INTEGER NOT NULL DEFAULT 0")

    }
}

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database" )
                .addMigrations(MIGRATION_13_14)
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

}
