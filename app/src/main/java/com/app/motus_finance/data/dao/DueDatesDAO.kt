package com.app.motus_finance.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.motus_finance.data.models.DueDates

@Dao
interface DueDatesDAO {

   @Insert
   suspend fun insertDueDate(dueDates: DueDates)

   @Query("SELECT dueDate FROM dueDate_entity")
   suspend fun getDueDate() : String

   @Query("DELETE FROM dueDate_entity")
   suspend fun deleteDueDate()
}