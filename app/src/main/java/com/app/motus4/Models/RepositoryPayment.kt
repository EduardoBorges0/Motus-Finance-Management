package com.app.motus4.Models

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Query
import com.app.motus4.Models.Room.DaoPayment
import com.app.motus4.Models.Room.DataClass.ModelPayment

class RepositoryPayment(private val daoPayment: DaoPayment) {
    suspend fun insertPayment(modelPayment: ModelPayment) = daoPayment.insertPayment(modelPayment)

    suspend fun updatePayment(payment: ModelPayment) = daoPayment.updatePayment(payment)

    suspend fun deletePayment(id: Long) = daoPayment.deletePayment(id)

    suspend fun getPayment(id: Long) = daoPayment.getPayment(id)
    suspend fun getPaymentAll() : ModelPayment? = daoPayment.getPaymentAll()

}