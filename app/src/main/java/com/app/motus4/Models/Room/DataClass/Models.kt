package com.app.simplemoney.Models.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_banks")
data class Bank(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val nameOfExpenses : String?,
    @ColumnInfo val name: String,
    @ColumnInfo val color: String,
    @ColumnInfo val img: String,
    @ColumnInfo val creditOrDebit: String? = null,
    @ColumnInfo val balance: Double? = null,
    @ColumnInfo val colorSpentsOrReceived: String,
    @ColumnInfo val date: String? = null,
    )
