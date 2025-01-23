package com.app.motus_finance.Models.DAO

import androidx.room.Dao
import androidx.room.Insert
import com.app.motus_finance.Models.DTO.DueDatesDTO
import com.app.motus_finance.Models.Entities.DueDates

@Dao
interface DueDatesDAO {

   @Insert
   suspend fun insertDueDate(dueDates: DueDates)
}