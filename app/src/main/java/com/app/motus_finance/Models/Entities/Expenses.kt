package com.app.motus_finance.Models.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_expenses",
    foreignKeys = [
        ForeignKey(
            entity = Banks::class,
            parentColumns = ["id"],
            childColumns = ["bankId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Expenses (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val bankId: Int?,
    @ColumnInfo val expenseDescription: String?,
    @ColumnInfo val value: Double?,
    @ColumnInfo val spentOrReceived: String? = null,
    @ColumnInfo val fixedOrVariable: String? = null,
    @ColumnInfo val date : String? = null,
    @ColumnInfo val classification : String? = null,
    val readyForDeletion: Boolean = false
)