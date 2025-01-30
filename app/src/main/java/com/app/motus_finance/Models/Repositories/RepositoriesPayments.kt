package com.app.motus_finance.Models.Repositories

import androidx.lifecycle.LiveData
import com.app.motus_finance.Models.DAO.PaymentDAO
import com.app.motus_finance.Models.DTO.PaymentDTO
import com.app.motus_finance.Models.Entities.Payments

class RepositoriesPayments(private val dao: PaymentDAO) {

    suspend fun updatePayments(newPayments: Double){
        return dao.updatePayment(newPayment = newPayments)
    }
    suspend fun insertPayments(payments: Payments){
        return dao.insertPayment(payments)
    }
     fun getPayments() : LiveData<Double>{
        return dao.getPayments()
    }
    suspend fun deletePayment(){
        return dao.deletePayment()
    }
}