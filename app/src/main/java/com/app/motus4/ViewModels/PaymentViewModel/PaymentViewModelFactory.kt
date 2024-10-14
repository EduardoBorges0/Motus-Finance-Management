package com.app.motus4.ViewModels.PaymentViewModel



import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.motus4.Models.RepositoryMonthly
import com.app.motus4.Models.RepositoryPayment
import com.app.simplemoney.Models.Repository
import com.app.simplemoney8.Models.RepositoryExpense

class PaymentViewModelFactory(
    private val application: Application,
    private val repositoryPayment: RepositoryPayment,
    private val repositoryExpense: RepositoryExpense
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentViewModel(application, repositoryPayment, repositoryExpense = repositoryExpense) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
