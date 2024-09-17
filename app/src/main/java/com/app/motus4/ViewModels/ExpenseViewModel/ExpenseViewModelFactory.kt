package com.app.motus4.ViewModels.ExpenseViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.motus4.Models.RepositoryMonthly
import com.app.simplemoney.Models.Repository
import com.app.simplemoney8.Models.RepositoryExpense

class ExpenseViewModelFactory(
    private val application: Application,
    private val repository: Repository,
    private val repositoryExpense: RepositoryExpense,
    private val repositoryMonthly: RepositoryMonthly
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(application, repositoryExpense, repository, repositoryMonthly) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
