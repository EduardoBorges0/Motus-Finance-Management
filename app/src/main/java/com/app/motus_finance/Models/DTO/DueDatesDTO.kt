package com.app.motus_finance.Models.DTO

import androidx.room.ColumnInfo
import com.app.motus_finance.Models.Entities.DueDates

data class DueDatesDTO (
    val dueDate : String? = null
)

fun DueDates.toDTO() : DueDatesDTO{
    return DueDatesDTO(
        dueDate = this.dueDate
    )
}

fun DueDatesDTO.toEntity(): DueDates{
    return DueDates(
        dueDate = this.dueDate
    )
}