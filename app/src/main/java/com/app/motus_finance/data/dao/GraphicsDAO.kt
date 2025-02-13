package com.app.motus_finance.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.app.motus_finance.data.models.Graphics

@Dao
interface GraphicsDAO {

    @Insert
    suspend fun insertGraphics(graphics: Graphics)
}