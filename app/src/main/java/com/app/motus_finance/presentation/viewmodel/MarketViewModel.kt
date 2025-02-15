package com.app.motus_finance.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.motus_finance.R
import com.app.motus_finance.data.models.Market
import com.app.motus_finance.domain.dto.ExpensesDTO
import com.app.motus_finance.domain.dto.MarketDTO
import com.app.motus_finance.domain.usecases.MarketUseCase
import com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.InsertMarketOrShopping.InsertMarket.InsertMarketModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(private val marketUseCase: MarketUseCase) : ViewModel() {
    val selectedTab = mutableIntStateOf(0)
    private val _alertDialog = MutableLiveData(false)
    val alertDialog: LiveData<Boolean> = _alertDialog
    var marketName by mutableStateOf("")

    private val _showLoading = MutableLiveData(false)
    val showLoading: LiveData<Boolean> = _showLoading

    fun setAlertDialog(value: Boolean) {
        _alertDialog.value = value
    }
    fun getMarketList(name: String?): List<InsertMarketModel> {
        return listOf(
            InsertMarketModel(
                name = name,
                backgroundColor = 0xFFD95B5B,
                img = R.drawable.supermarket,
                balance = null,
                colorSpentsOrReceived = 0xFF80BF6F,
                date = null,
                sum = null
            ),
            InsertMarketModel(
                name = name,
                backgroundColor = 0xFF0168B4,
                img = R.drawable.prezunic,
                balance = null,
                colorSpentsOrReceived = 0xFF6FBF1F,
                date = null,
                sum = null
            ),
            InsertMarketModel(
                name = name,
                backgroundColor = 0xFF0087C6,
                img = R.drawable.guanabarsa,
                balance = null,
                colorSpentsOrReceived = 0xFFF2CB05,
                date = null,
                sum = null
            ),
            InsertMarketModel(
                name = name,
                backgroundColor = 0xFF003399,
                img = R.drawable.assai,
                balance = null,
                colorSpentsOrReceived = 0xFFF28705,
                date = null,
                sum = null
            ),
            InsertMarketModel(
                name = name,
                backgroundColor = 0xFF012244,
                img = R.drawable.dom,
                balance = null,
                colorSpentsOrReceived = 0xFFF2CB05,
                date = null,
                sum = null
            ),
            InsertMarketModel(
                name = name,
                backgroundColor = 0xFFE51E27,
                img = R.drawable.rede_economia,
                balance = null,
                colorSpentsOrReceived = 0xFF14A647,
                date = null,
                sum = null
            ),
        )
    }

    fun insertBank(marketDTO: MarketDTO) {
        viewModelScope.launch {
            if (marketDTO.name != null||
                marketDTO.colorSpentsOrReceived != null||
                marketDTO.img != null||
                marketDTO.balance != null||
                marketDTO.date != null||
                marketDTO.color != null
            ) marketUseCase.insertBank(marketDTO)
        }

    }

    fun getAllBanks(): LiveData<List<Market>> {
        return marketUseCase.getAllBanks()
    }

    suspend fun updateBalanceWhenAddExpense(bankId: Int, expensesDTO: ExpensesDTO){
        return marketUseCase.updateBalance(bankId, expensesDTO)
    }
     fun deleteBanks(id: Int){
         viewModelScope.launch {
             marketUseCase.deleteBanks(id)
         }
    }
}