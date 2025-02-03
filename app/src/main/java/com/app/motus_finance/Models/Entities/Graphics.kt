package com.app.motus_finance.Models.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("graphics_entity")
data class Graphics (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo val monthly : String?,
    @ColumnInfo val value: Double?,
    @ColumnInfo val highestSpendingRating: String,
    @ColumnInfo val valueSpendingRating: Double
)