package com.app.motus_finance.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.app.motus_finance.domain.usecases.DueDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DueDateViewModel @Inject constructor(private val dueDatesUseCases: DueDateUseCase): ViewModel() {

    suspend fun insertDueDate(){
        return dueDatesUseCases.insertDueDate()
    }
    suspend fun deleteDueDate(){
        return dueDatesUseCases.deleteDueDate()
    }
}