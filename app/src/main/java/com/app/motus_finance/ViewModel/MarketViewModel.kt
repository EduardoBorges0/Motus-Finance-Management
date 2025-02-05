package com.app.motus_finance.ViewModel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.MarketDTO
import com.app.motus_finance.Models.Entities.Market
import com.app.motus_finance.Service.MarketService
import kotlinx.coroutines.launch

class MarketViewModel(private val bankService: MarketService) : ViewModel() {
    val selectedTab = mutableIntStateOf(0)
    private val _alertDialog = MutableLiveData(true)
    val alertDialog: LiveData<Boolean> = _alertDialog
    private val _showLoading = MutableLiveData(false)
    val showLoading: LiveData<Boolean> = _showLoading

    fun setAlertDialog(value: Boolean) {
        _alertDialog.value = value
    }
    suspend fun insertBank(marketDTO: MarketDTO): Boolean {
        if (marketDTO.name == null||
            marketDTO.colorSpentsOrReceived == null||
            marketDTO.img == null||
            marketDTO.balance == null||
            marketDTO.date == null||
            marketDTO.color == null
            ) return false

        return bankService.insertBank(marketDTO)
    }

    fun getAllBanks(): LiveData<List<Market>> {
        return bankService.getAllBanks() }

    suspend fun updateBalanceWhenAddExpense(bankId: Int, expensesDTO: ExpensesDTO){
        return bankService.updateBalance(bankId, expensesDTO)
    }
     fun deleteBanks(id: Int){
         viewModelScope.launch {
             bankService.deleteBanks(id)
         }
    }
}