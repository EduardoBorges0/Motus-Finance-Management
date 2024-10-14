package com.app.motus4.Models.Room.DataClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment")
class ModelPayment (
   @PrimaryKey val id: Long = 1L,
   @ColumnInfo val payment: Double?
)