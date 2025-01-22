package com.app.motus_finance.Models.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("dueDate_entity")
data class DueDates (
    @PrimaryKey(autoGenerate = false) val id : Long = 1,
    @ColumnInfo val dueDate : String? = null
)