package com.app.motus_finance.ViewModel

import androidx.lifecycle.ViewModel
import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Service.BankService
import kotlin.math.exp

class BanksViewModel(private val bankService: BankService) : ViewModel() {

    suspend fun insertBank(bankDTO: BankDTO): Boolean {

        if (bankDTO.name == null||
            bankDTO.colorSpentsOrReceived == null||
            bankDTO.img == null||
            bankDTO.balance == null||
            bankDTO.date == null||
            bankDTO.color == null
            ) return false

        return bankService.insertBank(bankDTO)
    }
    suspend fun updateBalance(bankDTO: BankDTO, expensesDTO: ExpensesDTO) : BankDTO{
        return bankService.updateBalance(bankDTO, expensesDTO)
    }
}