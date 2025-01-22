package com.app.motus_finance.Models.Repositories

import com.app.motus_finance.Models.DAO.DueDatesDAO
import com.app.motus_finance.Models.DTO.DueDatesDTO

class RepositoriesDueDates(private val dao: DueDatesDAO) {
    suspend fun insertDueDate(dueDatesDTO: DueDatesDTO){
        return dao.insertDueDate(dueDatesDTO)
    }
}