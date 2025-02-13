package com.app.motus_finance.domain.dto

import com.app.motus_finance.data.models.DueDates


data class DueDatesDTO (
    val dueDate : String? = null
)

fun DueDates.toDTO() : DueDatesDTO {
    return DueDatesDTO(
        dueDate = this.dueDate
    )
}

fun DueDatesDTO.toEntity(): DueDates{
    return DueDates(
        dueDate = this.dueDate
    )
}