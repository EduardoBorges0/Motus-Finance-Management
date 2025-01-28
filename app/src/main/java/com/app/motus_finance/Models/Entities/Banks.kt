package com.app.motus_finance.Models.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("bank_entity")
data class Banks (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val name : String?,
    @ColumnInfo val color: String?,
    @ColumnInfo val img: Int?,
    @ColumnInfo val balance: Double?,
    @ColumnInfo val colorSpentsOrReceived: String?,
    @ColumnInfo val date: String?,
    @ColumnInfo val sum: Double? = null
)