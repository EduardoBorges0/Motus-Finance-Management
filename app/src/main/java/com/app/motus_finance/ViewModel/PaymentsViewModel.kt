package com.app.motus_finance.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.PaymentDTO
import com.app.motus_finance.Service.PaymentsService
import kotlinx.coroutines.launch

class PaymentsViewModel(private val paymentsService: PaymentsService) : ViewModel() {
    fun updatePayment(expensesDTO: ExpensesDTO, payments: Double?){
        viewModelScope.launch {
            val paymentDTO = PaymentDTO(
                payment = payments
            )
            paymentsService.updatePayment(expensesDTO, paymentDTO)
        }
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