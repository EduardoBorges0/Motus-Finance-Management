package com.app.motus_finance.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.motus_finance.data.dao.DueDatesDAO
import com.app.motus_finance.data.dao.ExpensesDAO
import com.app.motus_finance.data.dao.GraphicsDAO
import com.app.motus_finance.data.dao.MarketDAO
import com.app.motus_finance.data.dao.PaymentDAO
import com.app.motus_finance.data.remote.AppDatabase
import com.app.motus_finance.data.repositories.RepositoriesDueDates
import com.app.motus_finance.data.repositories.RepositoriesExpenses
import com.app.motus_finance.data.repositories.RepositoriesGraphics
import com.app.motus_finance.data.repositories.RepositoriesMarket
import com.app.motus_finance.data.repositories.RepositoriesPayments
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMarketDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "market_database"
        ).addMigrations(MIGRATION_4_5) // Se houver migrações, adicionamos aqui
            .build()
    }

    @Provides
    @Singleton
    fun provideMarketDAO(database: AppDatabase): MarketDAO {
        return database.bankDao()
    }

    @Provides
    @Singleton
    fun provideExpensesDAO(database: AppDatabase): ExpensesDAO {
        return database.expenseDao()
    }

    @Provides
    @Singleton
    fun providePaymentDAO(database: AppDatabase): PaymentDAO {
        return database.paymentsDao()
    }

    @Provides
    @Singleton
    fun provideDueDatesDAO(database: AppDatabase): DueDatesDAO {
        return database.dueDateDao()
    }

    @Provides
    @Singleton
    fun provideGraphicsDAO(database: AppDatabase): GraphicsDAO {
        return database.graphicsDao()
    }

    @Provides
    @Singleton
    fun provideRepositoriesMarket(dao: MarketDAO): RepositoriesMarket {
        return RepositoriesMarket(dao)
    }

    @Provides
    @Singleton
    fun provideRepositoriesExpenses(dao: ExpensesDAO): RepositoriesExpenses {
        return RepositoriesExpenses(dao)
    }
    @Provides
    @Singleton
    fun provideRepositoriesPayments(dao: PaymentDAO): RepositoriesPayments {
        return RepositoriesPayments(dao)
    }
    @Provides
    @Singleton
    fun provideRepositoriesDueDates(dao: DueDatesDAO): RepositoriesDueDates {
        return RepositoriesDueDates(dao)
    }

    @Provides
    @Singleton
    fun provideRepositoriesGraphics(dao: GraphicsDAO): RepositoriesGraphics {
        return RepositoriesGraphics(dao)
    }
}
