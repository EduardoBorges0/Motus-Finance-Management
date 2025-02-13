package com.app.motus_finance.data.repositories

import androidx.lifecycle.LiveData
import com.app.motus_finance.data.dao.MarketDAO
import com.app.motus_finance.data.models.Market

class RepositoriesMarket(private val dao: MarketDAO) {

    suspend fun insertBank(market: Market){
        return dao.insertBank(market)
    }
    fun getAllBanks() : LiveData<List<Market>>{
        return dao.getAllBanks()
    }

    fun getDatesById(id: Int) : String{
        return dao.getDatesById(id)
    }
    suspend fun updateSum(bankId: Int, sum: Double){
        return dao.updateSum(bankId, sum)
    }

    suspend fun deleteBanks(id: Int){
        return dao.deleteBank(id)
    }
    suspend fun getBalanceById(id: Int) : Double{
        return dao.getBalanceById(id)
    }
    suspend fun updateBalance(bankId: Int, newBalance: Double){
        return dao.updateBalance(bankId, newBalance)
    }
    suspend fun updateBankDate(bankId: Int, newDate: String){
        return dao.updateBankDate(bankId, newDate)
    }
    suspend fun getAllDates() : List<String>{
        return dao.getAllDates()
    }

    suspend fun updateDatePlusMonth(date: String, id: Int){
        return dao.updateDatePlusMonth(date, id)
    }

    suspend fun sumAllBank() : Double{
        return dao.sumAllBank()
    }
    suspend fun updateAllSumToZero(sum: Double){
        return dao.updateAllSumToZero(sum)
    }


}