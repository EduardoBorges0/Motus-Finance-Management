package com.app.motus4.Models.Room.DataClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("monthlyExpense")
data class MonthlyExpense (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo val monthlyExpense: Double?,
    @ColumnInfo val monthly : String?,
    @ColumnInfo val yearExpense: Int?
)