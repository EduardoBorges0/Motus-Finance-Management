package com.app.motus_finance.Service

import com.app.motus_finance.Models.DTO.BankDTO
import com.app.motus_finance.Models.DTO.DueDatesDTO
import com.app.motus_finance.Models.DTO.GraphicsDTO
import com.app.motus_finance.Models.DTO.ToEntity
import com.app.motus_finance.Models.Repositories.RepositoriesGraphics
import com.app.motus_finance.UtilityClass.DateUtils
import java.time.LocalDate

class GraphicsService(private val repositoriesGraphics: RepositoriesGraphics) {

    suspend fun insertGraphics(dueDatesDTO: DueDatesDTO, bankDTO: BankDTO){
        val dueDate = dueDatesDTO.dueDate
        val lastDay = DateUtils.stringToLocalDate(dueDate.toString())

        if(lastDay == LocalDate.now()){
            val graphicsDTO = GraphicsDTO(
                monthly = lastDay.month.toString(),
                value = bankDTO.sum
            )
            repositoriesGraphics.insertGraphics(graphicsDTO.ToEntity())
        }
    }

}