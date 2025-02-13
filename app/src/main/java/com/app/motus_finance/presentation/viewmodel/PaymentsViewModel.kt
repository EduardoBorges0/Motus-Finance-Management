package com.app.motus_finance.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.motus_finance.domain.dto.ExpensesDTO
import com.app.motus_finance.domain.dto.PaymentDTO
import com.app.motus_finance.domain.usecases.PaymentsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(private val paymentsUseCases: PaymentsUseCases) : ViewModel() {
    private val _alertDialog = MutableLiveData(false)
    val alertDialog: LiveData<Boolean> = _alertDialog
    var selectedItem by mutableStateOf<String?>(null)

    fun setAlertDialog(value: Boolean) {
        _alertDialog.value = value
    }
    fun deletePayment(){
        viewModelScope.launch {
            paymentsUseCases.deletePayment()
        }
    }
    fun updatePayment(expensesDTO: ExpensesDTO){
        viewModelScope.launch {
            paymentsUseCases.updatePayment(expensesDTO)
        }
    }
    fun getPayment() : LiveData<Double>{
        return paymentsUseCases.getPayments()
    }

    fun insertPayments(payment: Double){
        viewModelScope.launch {
            val paymentDTO = PaymentDTO(
                payment = payment
            )
            paymentsUseCases.insertPayments(
                paymentDTO
            )
        }
    }
}