
package com.app.simplemoney8


fun TranslatedExpenseName(type : String): String {
    return when (type) {

        "Transport" -> "Transporte"
        "Sport" -> "Esporte"
        "Leisure" -> "Lazer"
        "Services" -> "Serviços"
        "Bills" -> "Contas"
        "Reserve" -> "Reserva"
        "Market" -> "Mercado"
        "Health" -> "Saúde"
        "Investment" -> "Investimento"
        "Pet" -> "Animal"
        "Trips" -> "Viagens"
        "Education" -> "Educação"
        "Others" -> "Outros"


        "Transporte" -> "Transporte"
        "Esporte" -> "Esporte"
        "Lazer" -> "Lazer"
        "Serviços" -> "Serviços"
        "Contas" -> "Contas"
        "Reserva" -> "Reserva"
        "Mercado" -> "Mercado"
        "Saúde" -> "Saúde"
        "Investimento" -> "Investimento"
        "Animal" -> "Animal"
        "Viagens" -> "Viagens"
        "Educação" -> "Educação"
        "Outros" -> "Outros"

        "Deporte" -> "Esporte"
        "Ocio" -> "Lazer"
        "Servicios" -> "Serviços"
        "Facturas" -> "Contas"
        "Reserve" -> "Reserva"
        "Salud" -> "Saúde"
        "Inversión" -> "Investimento"
        "Mascota" -> "Animal"
        "Viajes" -> "Viagens"
        "Educación" -> "Educação"
        "Otros" -> "Outros"
        else -> ""
    }
}
