package com.app.motus_finance.presentation.UtilityClass

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


object UtilityClass {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun stringToLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, formatter)
    }

    fun currencyFormat(value: Double):String{
        val currency = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return currency.format(value)
    }
    fun formatToCurrency(input: String): String {
        val cleanedInput = input.replace(Regex("[^0-9]"), "")

        val parsedValue = cleanedInput.toLongOrNull() ?: 0L

        return if (parsedValue == 0L) {
            "0.00"
        } else {
            val formattedValue = parsedValue.toString().padStart(3, '0')
            val integerPart = formattedValue.dropLast(2)
            val decimalPart = formattedValue.takeLast(2)

            val integerWithThousandsSeparator =
                integerPart.reversed().chunked(3).joinToString(".").reversed()

            "$integerWithThousandsSeparator,$decimalPart"
        }
    }


}
