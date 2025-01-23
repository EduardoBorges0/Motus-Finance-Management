package com.app.motus_finance.Models.DAO

import androidx.room.Dao
import androidx.room.Insert
import com.app.motus_finance.Models.DTO.GraphicsDTO
import com.app.motus_finance.Models.Entities.Graphics

@Dao
interface GraphicsDAO {

    @Insert
    suspend fun insertGraphics(graphics: Graphics)
}