package com.app.motus_finance.domain.usecases

import com.app.motus_finance.data.repositories.RepositoriesDueDates
import com.app.motus_finance.data.repositories.RepositoriesExpenses
import com.app.motus_finance.data.repositories.RepositoriesGraphics
import com.app.motus_finance.data.repositories.RepositoriesMarket
import com.app.motus_finance.domain.dto.GraphicsDTO
import com.app.motus_finance.domain.dto.ToEntity
import com.app.motus_finance.presentation.UtilityClass.DateUtils
import java.time.LocalDate
import javax.inject.Inject

class GraphicsUseCase @Inject constructor(
    private val repositoriesGraphics: RepositoriesGraphics,
    private val repositoriesDueDates: RepositoriesDueDates,
    private val repositoriesExpenses: RepositoriesExpenses,
    private val repositoriesBank: RepositoriesMarket
) {

    suspend fun insertGraphics(){
        val dueDate = repositoriesDueDates.getDueDate()
        val lastDay = DateUtils.stringToLocalDate(dueDate)
        val getAllSpendingRatings = repositoriesExpenses.getHighestSpendingRating()?.get(0)

        if(lastDay == LocalDate.now()){
            val sum = repositoriesBank.sumAllBank()
            val graphicsDTO = GraphicsDTO(
                monthly = lastDay.month.toString(),
                value = sum,
                highestSpendingRating = getAllSpendingRatings?.classification.toString(),
                valueSpendingRating = getAllSpendingRatings?.total ?: 0.0

            )
            repositoriesBank.updateAllSumToZero(0.0)
            repositoriesGraphics.insertGraphics(graphicsDTO.ToEntity())
        }
    }

}