package com.app.motus_finance.Service

import android.util.Log
import com.app.motus_finance.Models.DTO.DueDatesDTO
import com.app.motus_finance.Models.DTO.toEntity
import com.app.motus_finance.Models.Repositories.RepositoriesBank
import com.app.motus_finance.Models.Repositories.RepositoriesDueDates
import com.app.motus_finance.UtilityClass.DateUtils

class DueDateService(private val repositoriesBank: RepositoriesBank,
                     private val repositoriesDueDates: RepositoriesDueDates) {
    suspend fun insertDueDate(){
        val dueDates = repositoriesBank.getAllDates()
        val plusDay = DateUtils.stringToLocalDate(dueDates.max().toString()).plusDays(1)
        val dueDatesDTO = DueDatesDTO(
            dueDate = plusDay.toString()
        )
        repositoriesDueDates.insertDueDate(dueDatesDTO.toEntity())
    }
    suspend fun deleteDueDate(){
        return repositoriesDueDates.deleteDueDate()
    }
}