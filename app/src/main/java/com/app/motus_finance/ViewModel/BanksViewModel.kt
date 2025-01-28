package com.app.motus_finance.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.toEntity
import com.app.motus_finance.Models.Entities.Banks
import com.app.motus_finance.Service.BankService
import kotlinx.coroutines.launch
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

    fun getAllBanks(): LiveData<List<Banks>> = bankService.getAllBanks()

    suspend fun updateBalanceWhenAddExpense(bankDTO: BankDTO, expensesDTO: ExpensesDTO) : Banks{
        return bankService.updateBalance(bankDTO, expensesDTO)
    }
     fun deleteBanks(id: Int){
         viewModelScope.launch {
             bankService.deleteBanks(id)
         }
    }
}