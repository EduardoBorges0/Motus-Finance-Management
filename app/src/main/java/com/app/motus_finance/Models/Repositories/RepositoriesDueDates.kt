package com.app.motus_finance.Models.Repositories

import com.app.motus_finance.Models.DAO.DueDatesDAO
import com.app.motus_finance.Models.DTO.DueDatesDTO
import com.app.motus_finance.Models.Entities.DueDates

class RepositoriesDueDates(private val dao: DueDatesDAO) {
    suspend fun insertDueDate(dueDates: DueDates){
        return dao.insertDueDate(dueDates)
    }
    suspend fun getDueDate() : String{
        return dao.getDueDate()
    }

}