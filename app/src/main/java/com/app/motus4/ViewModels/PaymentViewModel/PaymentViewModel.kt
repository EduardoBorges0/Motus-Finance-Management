package com.app.motus4.ViewModels.PaymentViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.motus4.Models.RepositoryPayment
import com.app.motus4.Models.Room.DataClass.ModelPayment
import com.app.simplemoney8.Models.RepositoryExpense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentViewModel(application: Application,
                       private var repositoryPayment: RepositoryPayment,
                       private var repositoryExpense: RepositoryExpense) : AndroidViewModel(application) {
    val livedata = MutableLiveData<ModelPayment?>()
    fun insertPayment(modelPayment: ModelPayment) {
        viewModelScope.launch {
            Log.d("PAYMENT", "YOUR PAYMENT $modelPayment")
            repositoryPayment.insertPayment(modelPayment) // Sem o return
        }
    }

     fun updatePaymentByBank(bankId: Int) {
        viewModelScope.launch {
            val spent = repositoryExpense.getTotalSpentByBank(bankId) ?: 0.0
            Log.d("VALOR SPENT By BANK", "VALOR SPENT $spent")
            val received = repositoryExpense.getTotalReceivedByBank(bankId) ?: 0.0
            Log.d("VALOR SPENT By BANK", "VALOR RECEIVED $received")
            val plus = spent - received
            Log.d("VALOR SPENT By BANK", "VALOR TOTAL $plus")
            val paymentValue = repositoryPayment.getPayment(1L)
            val count = paymentValue?.payment?.plus(plus)
            val pay = ModelPayment(payment = count)
            repositoryPayment.updatePayment(pay)
            Log.d("VALOR SPENT", "VALOR PAYMENT ${pay.payment}")

        }
    }
    suspend fun updatePayment(){
        CoroutineScope(Dispatchers.IO).launch {
            val spent = repositoryExpense.getSpent() ?: 0.0
            Log.d("VALOR SPENT", "VALOR SPENT $spent")
            val received = repositoryExpense.getReceived() ?: 0.0
            Log.d("VALOR SPENT", "VALOR RECEIVED $received")
            val plus = spent - received
            Log.d("VALOR SPENT", "VALOR TOTAL $plus")
            val paymentValue = repositoryPayment.getPayment(1L)
                val count = paymentValue?.payment?.minus(plus)
                val pay = ModelPayment(payment = count)
                viewModelScope.launch {
                    repositoryPayment.updatePayment(pay)
                    Log.d("VALOR SPENT", "VALOR PAYMENT ${pay.payment}")

                }
        }
    }
    suspend fun updatePaymentForDeleteExpense(value: Double, type: String){
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("VALOR SPENT", "VALOR TOTAL $value")
            val paymentValue = repositoryPayment.getPayment(1L)
            val count = when(type){
                "Spent" -> paymentValue?.payment?.plus(value)
                "Received" -> paymentValue?.payment?.minus(value)
                else -> throw IllegalArgumentException("Invalid payment type: $type")
            }
            val pay = ModelPayment(payment = count)
            viewModelScope.launch {
                repositoryPayment.updatePayment(pay)
                Log.d("VALOR SPENT", "VALOR PAYMENT ${pay.payment}")

            }
        }
    }
    suspend fun updatePaymentIsNotExist(value: Double, type: String){
        CoroutineScope(Dispatchers.IO).launch {
            val paymentValue = repositoryPayment.getPayment(1L)
            val spentOrReceived = when(type){
                "Spent" -> paymentValue?.payment?.minus(value)
                "Received" -> paymentValue?.payment?.plus(value) // Add handling for "Received"
                else -> throw IllegalArgumentException("Invalid payment type: $type") // Handle unexpected types
            }
            val pay = ModelPayment(payment = spentOrReceived)
            viewModelScope.launch {
                repositoryPayment.updatePayment(pay)
                Log.d("VALOR SPENT", "VALOR PAYMENT ${pay.payment}")

            }
        }
    }


    suspend fun deletePayment(id: Long){
        return repositoryPayment.deletePayment(id)
    }
     suspend fun getPayment(){
         try {
             livedata.postValue( repositoryPayment.getPayment(1))
             Log.d("ISSO AI VIEWMODEL", "${livedata.value}")
         }catch (e: Exception){
             Log.d("EXCEÇÃO", "ESSE ERRO AE MEU NOBRE ${e.message}")

         }

     }
}