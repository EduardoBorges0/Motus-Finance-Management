package com.app.motus_finance.data.repositories

import com.app.motus_finance.data.dao.DueDatesDAO
import com.app.motus_finance.data.models.DueDates


class RepositoriesDueDates(private val dao: DueDatesDAO) {
    suspend fun insertDueDate(dueDates: DueDates){
        return dao.insertDueDate(dueDates)
    }
    suspend fun getDueDate() : String{
        return dao.getDueDate()
    }

    suspend fun deleteDueDate(){
        return dao.deleteDueDate()
    }


}