package com.app.motus_finance.Models.DTO

import com.app.motus_finance.Models.Entities.Graphics

data class GraphicsDTO (
    val monthly: String?,
    val value: Double?,
    val highestSpendingRating : String,
    val valueSpendingRating: Double
)

fun Graphics.ToDTO() : GraphicsDTO{
    return GraphicsDTO(
        monthly = this.monthly,
        value = this.value,
        highestSpendingRating = this.highestSpendingRating,
        valueSpendingRating = this.valueSpendingRating
    )
}

fun GraphicsDTO.ToEntity() : Graphics{
    return Graphics(
        monthly = this.monthly,
        value = this.value,
        highestSpendingRating = this.highestSpendingRating,
        valueSpendingRating = this.valueSpendingRating
    )
}