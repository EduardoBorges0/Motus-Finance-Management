package com.app.motus4.Models.Room.DataClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("table_language")
data class ModelsLanguage(
    @PrimaryKey() val id: Int = 0,
    @ColumnInfo("languageCode") val language : String
)
