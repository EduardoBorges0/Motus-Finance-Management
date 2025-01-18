package com.app.motus_finance.Models.DTO

import com.app.motus_finance.Models.Entities.Payments

data class PaymentDTO (
    val payment: Double?
)

fun PaymentDTO.toEntity() : Payments{
    return Payments(
        payment = this.payment
    )
}

fun Payments.toDTO() : PaymentDTO{
    return PaymentDTO(
        payment = this.payment
    )
}