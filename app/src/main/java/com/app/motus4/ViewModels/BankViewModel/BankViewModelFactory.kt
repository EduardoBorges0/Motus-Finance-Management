package com.app.motus4.ViewModels.BankViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.simplemoney.Models.Repository
import com.app.simplemoney8.Models.RepositoryLanguage

class BankViewModelFactory(
    private val application: Application,
    private val repository: Repository,
    private val repositoryLanguage: RepositoryLanguage
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BankViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BankViewModel(application, repository, repositoryLanguage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
