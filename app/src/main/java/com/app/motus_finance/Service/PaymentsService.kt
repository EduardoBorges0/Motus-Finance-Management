package com.app.motus_finance.Service

import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.PaymentDTO
import com.app.motus_finance.Models.Repositories.RepositoriesPayments

class PaymentsService(private val repositoriesPayments: RepositoriesPayments) {
    suspend fun updatePayment(expensesDTO: ExpensesDTO, paymentDTO: PaymentDTO): Double{
        if(expensesDTO.spentOrReceived == "Spent"){
            val spent = expensesDTO.value?.let { paymentDTO.payment?.minus(it) }
            spent?.let { repositoriesPayments.updatePayments(it) }
            if (spent != null) {
                return spent.toDouble()
            }
        }else if(expensesDTO.spentOrReceived == "Received"){
            val received = expensesDTO.value?.let { paymentDTO.payment?.plus(it) }
            received?.let { repositoriesPayments.updatePayments(it) }
            if (received != null) {
                return received.toDouble()
            }
        }
        return 0.0
    }
    suspend fun insertPayments(paymentDTO: PaymentDTO){
        repositoriesPayments.insertPayments(paymentDTO)
    }
}