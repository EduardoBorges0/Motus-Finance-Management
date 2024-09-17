package com.app.motus4.ViewModels.BankViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.motus4.Models.Room.DataClass.ModelsLanguage
import com.app.simplemoney.Models.Repository
import com.app.simplemoney.Models.Room.Bank
import com.app.simplemoney6.Models.Room.DataClass.Expense
import com.app.simplemoney8.Models.RepositoryExpense
import com.app.simplemoney8.Models.RepositoryLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BankViewModel(application: Application, private val repository: Repository, private val repositoryLanguage: RepositoryLanguage) : AndroidViewModel(application) {
    fun getAllBanks(): LiveData<List<Bank>> = repository.getAllBanks()

    suspend fun getBankById(id: Int): Bank? {
        return withContext(Dispatchers.IO) {
            repository.getBankById(id)
        }
    }




    fun updateBalanceForExpense(bankId: Int, value: Double, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBalanceForExpense(bankId, value, type)
        }
    }

     fun getAllBankClosureDates(): List<String> = repository.getAllBankClosureDates()

    fun insertBank(name: String, color: String, img: String, creditOrDebit: String?, balance: Double?, colorSpentOrReceived : String, date : String?, nameOfExpenses : String?) {
        Log.d("BankViewModel", "Inserting expense: name=$name, color=$color, img=$img, creditOrDebit=$creditOrDebit, date = $date, colorSpentOrReceveid = $colorSpentOrReceived")

        val bank = Bank(name = name, color = color, img = img, creditOrDebit = creditOrDebit, balance = balance, colorSpentsOrReceived = colorSpentOrReceived, date = date, nameOfExpenses = nameOfExpenses)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertBank(bank)
        }
    }

    fun insertLanguage(language : ModelsLanguage) = viewModelScope.launch { repositoryLanguage.insertLanguage(language) }

    suspend fun updateLanguage(): String? {
        return repositoryLanguage.updateLanguage()
    }

    fun deleteBank(bank: Bank) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBank(bank)
        }
    }




}
