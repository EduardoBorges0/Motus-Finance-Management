package com.app.motus_finance.Models.DTO

import com.app.motus_finance.Models.Entities.Banks

data class BankDTO (
    val name: String?,
    val color: String?,
    val img: String?,
    val balance: Double?,
    val colorSpentsOrReceived: String?,
    val date: String?,
    val sum: Double?
)
fun Banks.toDTO(): BankDTO {
    return BankDTO(
        name = this.name,
        color = this.color,
        img = this.img,
        balance = this.balance,
        colorSpentsOrReceived = this.colorSpentsOrReceived,
        date = this.date,
        sum = this.sum
    )
}
fun BankDTO.toEntity(): Banks {
    return Banks(
        name = this.name,
        color = this.color,
        img = this.img,
        balance = this.balance,
        colorSpentsOrReceived = this.colorSpentsOrReceived,
        date = this.date,
        sum = this.sum
    )
}


