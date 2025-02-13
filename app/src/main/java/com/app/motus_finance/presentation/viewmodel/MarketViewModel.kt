package com.app.motus_finance.presentation.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.motus_finance.data.models.Market
import com.app.motus_finance.domain.dto.ExpensesDTO
import com.app.motus_finance.domain.dto.MarketDTO
import com.app.motus_finance.domain.usecases.MarketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(private val marketUseCase: MarketUseCase) : ViewModel() {
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

        return marketUseCase.insertBank(marketDTO)
    }

    fun getAllBanks(): LiveData<List<Market>> {
        return marketUseCase.getAllBanks() }

    suspend fun updateBalanceWhenAddExpense(bankId: Int, expensesDTO: ExpensesDTO){
        return marketUseCase.updateBalance(bankId, expensesDTO)
    }
     fun deleteBanks(id: Int){
         viewModelScope.launch {
             marketUseCase.deleteBanks(id)
         }
    }
}