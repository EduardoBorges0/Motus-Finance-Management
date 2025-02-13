package com.app.motus_finance.data.repositories

import androidx.lifecycle.LiveData
import com.app.motus_finance.data.dao.PaymentDAO
import com.app.motus_finance.data.models.Payments

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