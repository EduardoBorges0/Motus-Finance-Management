package com.app.motus_finance.domain.dto

import com.app.motus_finance.data.models.Market

data class MarketDTO (
    val name: String?,
    val color: String?,
    val img: Int?,
    val balance: Double?,
    val colorSpentsOrReceived: String?,
    val date: String?,
    val sum: Double?
)
fun Market.toDTO(): MarketDTO {
    return MarketDTO(
        name = this.name,
        color = this.color,
        img = this.img,
        balance = this.balance,
        colorSpentsOrReceived = this.colorSpentsOrReceived,
        date = this.date,
        sum = this.sum
    )
}
fun MarketDTO.toEntity(): Market {
    return Market(
        name = this.name,
        color = this.color,
        img = this.img,
        balance = this.balance,
        colorSpentsOrReceived = this.colorSpentsOrReceived,
        date = this.date,
        sum = this.sum
    )
}


