package com.app.motus4.View

import android.util.Log
import java.text.DecimalFormat

class FormatNumber {
    private var currentNumber = "0.00"

    fun formatNumber(input: String): String {
        // Remove o ponto decimal do número atual
        val cleanNumber = currentNumber.replace(".", "")
        // Adiciona o novo dígito ao final
        val newNumber = cleanNumber + input

        // Formata o número com duas casas decimais
        val formattedNumber = if (newNumber.length > 2) {
            val integerPart = newNumber.substring(0, newNumber.length - 2)
            val decimalPart = newNumber.substring(newNumber.length - 2)
            "$integerPart.$decimalPart"
        } else {
            "0.$newNumber".padStart(4, '0')
        }

        currentNumber = formattedNumber
        return currentNumber
    }
}
