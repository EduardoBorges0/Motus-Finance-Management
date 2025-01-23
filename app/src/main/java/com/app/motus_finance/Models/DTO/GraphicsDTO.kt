package com.app.motus_finance.Models.DTO

import com.app.motus_finance.Models.Entities.Graphics

data class GraphicsDTO (
    val monthly: String?,
    val value: Double?
)

fun Graphics.ToDTO() : GraphicsDTO{
    return GraphicsDTO(
        monthly = this.monthly,
        value = this.value
    )
}

fun GraphicsDTO.ToEntity() : Graphics{
    return Graphics(
        monthly = this.monthly,
        value = this.value
    )
}