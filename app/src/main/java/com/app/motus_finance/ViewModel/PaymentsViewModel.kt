package com.app.motus_finance.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.PaymentDTO
import com.app.motus_finance.Service.PaymentsService
import kotlinx.coroutines.launch

class PaymentsViewModel(private val paymentsService: PaymentsService) : ViewModel() {
    private val _alertDialog = MutableLiveData(false)
    val alertDialog: LiveData<Boolean> = _alertDialog
    var selectedItem by mutableStateOf<String?>(null)

    fun setAlertDialog(value: Boolean) {
        _alertDialog.value = value
    }
    fun deletePayment(){
        viewModelScope.launch {
            paymentsService.deletePayment()
        }
    }
    fun updatePayment(expensesDTO: ExpensesDTO){
        viewModelScope.launch {
            paymentsService.updatePayment(expensesDTO)
        }
    }
    fun getPayment() : LiveData<Double>{
        return paymentsService.getPayments()
    }

    fun insertPayments(payment: Double){
        viewModelScope.launch {
            val paymentDTO = PaymentDTO(
                payment = payment
            )
            paymentsService.insertPayments(
                paymentDTO
            )
        }
    }
}