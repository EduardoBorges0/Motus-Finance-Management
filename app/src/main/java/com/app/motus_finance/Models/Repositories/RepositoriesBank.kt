package com.app.motus_finance.Models.Repositories

import androidx.lifecycle.LiveData
import com.app.motus_finance.Models.DAO.BankDao
import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.Entities.Banks

class RepositoriesBank(private val dao: BankDao) {

    suspend fun insertBank(banks: Banks){
        return dao.insertBank(banks)
    }
    fun getAllBanks() : LiveData<List<Banks>>{
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