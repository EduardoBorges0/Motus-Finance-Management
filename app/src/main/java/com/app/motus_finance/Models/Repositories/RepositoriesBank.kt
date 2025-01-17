package com.app.motus_finance.Models.Repositories

import com.app.motus_finance.Models.DAO.BankDao
import com.app.motus_finance.Models.DTO.BankDTO

class RepositoriesBank(private val dao: BankDao) {

    suspend fun insertBank(bankDTO: BankDTO){
        return dao.insertBank(bankDTO)
    }

    suspend fun updateBalance(bankId: Int, newBalance: Double){
        return dao.updateBalance(bankId, newBalance)
    }

}