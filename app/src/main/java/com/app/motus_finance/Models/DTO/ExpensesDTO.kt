package com.app.motus_finance.Models.DTO

import com.app.motus_finance.Models.Entities.Expenses


data class ExpensesDTO (
    val bankId: Int?,
    val expenseDescription: String?,
    val value: Double?,
    val spentOrReceived: String? = null,
    val fixedOrVariable: String? = null,
    val date : String? = null,
    val dueDate : String?,
    val classification : String? = null,
    val readyForDeletion: Boolean = false
)

fun Expenses.toDTO() : ExpensesDTO{
    return ExpensesDTO(
        bankId = this.bankId,
        expenseDescription = this.expenseDescription,
        value = this.value,
        spentOrReceived = this.spentOrReceived,
        fixedOrVariable = this.fixedOrVariable,
        date = this.date,
        dueDate = this.dueDate,
        classification = this.classification,
        readyForDeletion = this.readyForDeletion
    )
}
fun ExpensesDTO.toEntity() : Expenses{
    return Expenses(
        bankId = this.bankId,
        expenseDescription = this.expenseDescription,
        value = this.value,
        spentOrReceived = this.spentOrReceived,
        fixedOrVariable = this.fixedOrVariable,
        date = this.date,
        dueDate = this.dueDate,
        classification = this.classification,
        readyForDeletion = this.readyForDeletion
    )
}