package com.app.motus_finance.ViewModel

import android.view.View
import androidx.lifecycle.ViewModel
import com.app.motus_finance.Service.DueDateService

class DueDateViewModel(private val service: DueDateService): ViewModel() {

    suspend fun insertDueDate(){
        return service.insertDueDate()
    }
}