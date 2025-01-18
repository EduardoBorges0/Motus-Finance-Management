package com.app.motus_finance.Models.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("payment_table")
data class Payments (
    @PrimaryKey(autoGenerate = false) val id : Long = 1,
    @ColumnInfo val payment : Double? = null
)