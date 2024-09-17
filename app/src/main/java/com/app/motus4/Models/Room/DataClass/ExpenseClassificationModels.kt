package com.app.simplemoney6.Models.Room.DataClass

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.app.simplemoney.Models.Room.Bank

@Entity(
    tableName = "table_expensesClassification",
    foreignKeys = [
        ForeignKey(
            entity = Expense::class,
            parentColumns = ["id"],
            childColumns = ["idClassification"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExpenseClassificationModels(
   @PrimaryKey(autoGenerate = true) val id : Int = 0,
   val idClassification: Int,
   val icon : String?,
   val ExpenseClassification : String?,
   val totalValue : Double?
)
