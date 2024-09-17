package com.app.simplemoney8.Models.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.motus4.Models.Room.DataClass.ModelsLanguage

@Dao
interface DaoLanguage {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(modelsLanguage: ModelsLanguage)

    @Query("SELECT languageCode FROM table_language ORDER BY id DESC LIMIT 1")
    suspend fun getSavedLanguage(): String?

}