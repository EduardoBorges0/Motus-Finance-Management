package com.app.simplemoney8.Models

import androidx.room.Insert
import androidx.room.Update
import com.app.motus4.Models.Room.DataClass.ModelsLanguage
import com.app.simplemoney8.Models.Room.DaoLanguage

class RepositoryLanguage(private val daoLanguage: DaoLanguage) {

    suspend fun insertLanguage(language : ModelsLanguage) = daoLanguage.insertLanguage(language)
    suspend fun updateLanguage() = daoLanguage.getSavedLanguage()

}