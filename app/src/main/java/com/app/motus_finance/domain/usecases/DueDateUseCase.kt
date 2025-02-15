package com.app.motus_finance.domain.usecases

import com.app.motus_finance.data.repositories.RepositoriesDueDates
import com.app.motus_finance.data.repositories.RepositoriesMarket
import com.app.motus_finance.domain.dto.DueDatesDTO
import com.app.motus_finance.domain.dto.toEntity
import com.app.motus_finance.presentation.UtilityClass.UtilityClass
import javax.inject.Inject

class DueDateUseCase @Inject constructor(private val repositoriesBank: RepositoriesMarket,
                                         private val repositoriesDueDates: RepositoriesDueDates
) {
    suspend fun insertDueDate(){
        val dueDates = repositoriesBank.getAllDates()
        val plusDay = UtilityClass.stringToLocalDate(dueDates.max().toString()).plusDays(1)
        val dueDatesDTO = DueDatesDTO(
            dueDate = plusDay.toString()
        )
        repositoriesDueDates.insertDueDate(dueDatesDTO.toEntity())
    }
    suspend fun deleteDueDate(){
        return repositoriesDueDates.deleteDueDate()
    }
}