package com.app.motus_finance.Models.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.motus_finance.Models.DTO.PaymentDTO

@Dao
interface PaymentDAO {

    @Query("UPDATE payment_table SET payment = :newPayment WHERE id = 1")
    suspend fun updatePayment(newPayment : Double) : Double

    @Insert
    suspend fun insertPayment(paymentDTO: PaymentDTO)

}