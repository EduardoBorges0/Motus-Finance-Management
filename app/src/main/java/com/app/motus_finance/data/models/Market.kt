package com.app.motus_finance.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("market_entity")
data class Market (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val name : String?,
    @ColumnInfo val color: String?,
    @ColumnInfo val img: Int?,
    @ColumnInfo val balance: Double?,
    @ColumnInfo val colorSpentsOrReceived: String?,
    @ColumnInfo val date: String?,
    @ColumnInfo val sum: Double? = null
)