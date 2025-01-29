package com.app.motus_finance.UtilityClass

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun stringToLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, formatter)
    }

    fun currencyFormat(value: Double):String{
        val currency = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return currency.format(value)
    }
}
