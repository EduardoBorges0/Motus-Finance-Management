package com.app.simplemoney6.Models.Room.DataClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.app.simplemoney.Models.Room.Bank

@Entity(
    tableName = "table_expenses",
    foreignKeys = [
        ForeignKey(
            entity = Bank::class,
            parentColumns = ["id"],
            childColumns = ["bankId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val bankId: Int?,
    @ColumnInfo val description: String?,
    @ColumnInfo val value: Double?,
    @ColumnInfo val spentOrReceived: String? = null,
    @ColumnInfo val type: String? = null,
    @ColumnInfo val date : String? = null,
    @ColumnInfo val expenseClassification : String? = null,
    val readyForDeletion: Boolean = false

)
