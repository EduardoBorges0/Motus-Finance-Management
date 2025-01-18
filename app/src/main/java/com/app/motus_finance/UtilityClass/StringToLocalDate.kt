package com.app.motus_finance.UtilityClass

import java.time.LocalDate
import java.time.format.DateTimeFormatter



object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Define o formato padrão de data

    fun stringToLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, formatter) // Converte a string em LocalDate
    }
}
