package com.app.motus_finance.Service

import androidx.lifecycle.LiveData
import com.app.motus_finance.Models.DTO.ExpensesDTO
import com.app.motus_finance.Models.DTO.PaymentDTO
import com.app.motus_finance.Models.DTO.toEntity
import com.app.motus_finance.Models.Repositories.RepositoriesPayments

class PaymentsService(private val repositoriesPayments: RepositoriesPayments) {
    suspend fun updatePayment(expensesDTO: ExpensesDTO): Double{
        val payments = repositoriesPayments.getPayments().value
        if(expensesDTO.spentOrReceived == "Spent"){
            val spent = expensesDTO.value?.let { payments?.minus(it) }
            spent?.let { repositoriesPayments.updatePayments(it) }
            if (spent != null) {
                return spent.toDouble()
            }
        }else if(expensesDTO.spentOrReceived == "Received"){
            val received = expensesDTO.value?.let { payments?.plus(it) }
            received?.let { repositoriesPayments.updatePayments(it) }
            if (received != null) {
                return received.toDouble()
            }
        }
        return 0.0
    }
    suspend fun insertPayments(paymentDTO: PaymentDTO){
        repositoriesPayments.insertPayments(paymentDTO.toEntity())
    }
    fun getPayments() : LiveData<Double> {
        return repositoriesPayments.getPayments()
    }
    suspend fun deletePayment(){
        return repositoriesPayments.deletePayment()
    }
}