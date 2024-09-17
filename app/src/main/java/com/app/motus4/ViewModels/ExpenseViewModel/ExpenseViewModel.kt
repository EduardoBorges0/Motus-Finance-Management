package com.app.motus4.ViewModels.ExpenseViewModel

import androidx.lifecycle.AndroidViewModel
import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.motus4.Models.RepositoryMonthly
import com.app.motus4.Models.Room.DataClass.MonthlyExpense
import com.app.simplemoney.Models.Repository
import com.app.simplemoney.Models.Room.Bank
import com.app.simplemoney6.Models.Room.DataClass.Expense

import com.app.simplemoney8.Models.RepositoryExpense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ExpenseViewModel(
    application: Application,
    private val repositoryExpense: RepositoryExpense,
    private val repository: Repository,
    private val repositoryMonthly: RepositoryMonthly
    )  : AndroidViewModel(application) {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun String.toLocalDateOrNull(formatter: DateTimeFormatter): LocalDate? {

        return try {
            LocalDate.parse(this, dateFormatter)

        } catch (e: DateTimeParseException) {
            null
        }
    }

    fun getTotalByClassification(classification: String, onResult: (Double?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val total = repositoryExpense.getTotalByClassification(classification)
            onResult(total)
        }
    }

    fun getExpensesForBank(bankId: Int): LiveData<List<Expense>> = repositoryExpense.getExpensesForBank(bankId)

    fun getExpenseForClassification(bankId: Int, expenseClassification : String?): LiveData<List<Expense>> = repositoryExpense.getExpenseForClassification(bankId, expenseClassification)

    suspend fun getTotalByClassificationAndBankId(bankId: Int, classification: String): Double? = repositoryExpense.getTotalByClassificationAndBankId(bankId, classification)

    suspend fun getTotalSpent(): Double? = repositoryExpense.getTotalSpent()

    fun deleteExpenseById(bank: Bank, expenseId: Int, expenseValue: Double, type: String) {
        viewModelScope.launch {
            try {
                // Obter o banco atualizado
                val updatedBank = repository.getBankById(bank.id)
                if (updatedBank == null) {
                    Log.e("DeleteExpense", "Bank not found with ID: ${bank.id}")
                    return@launch
                }
                val newBalance = when (type) {
                    "Spent" -> updatedBank.balance?.plus(expenseValue)
                    "Received" -> updatedBank.balance?.minus(expenseValue)
                    else -> {
                        Log.e("DeleteExpense", "Invalid expense type: $type")
                        updatedBank.balance
                    }
                }

                // Verificar se o novo saldo é válido
                if (newBalance != null) {
                    // Atualizar o banco com o novo saldo
                    repository.updateBank(updatedBank.copy(balance = newBalance))
                } else {
                    Log.e("DeleteExpense", "Failed to calculate new balance.")
                }

                // Excluir a despesa
                repositoryExpense.deleteExpenseById(expenseId)
            } catch (e: Exception) {
                // Log ou tratamento de erro
                Log.e("DeleteExpense", "Error deleting expense: ${e.message}")
            }
        }
    }

    fun insertExpense(id: Int, description: String, value: Double, spentOrReceived: String, type: String?, date: String?, expenseClassification : String) {
        Log.d("BankViewModel", "Inserting expense: bankId=$id, description=$description, value=$value, spentOrReceived=$spentOrReceived, date= $date, expenseClassification = $expenseClassification")
        val expense = Expense(bankId = id, description = description, value = value, spentOrReceived = spentOrReceived, type = type, expenseClassification = expenseClassification)
        viewModelScope.launch(Dispatchers.IO) {
            repositoryExpense.insertExpense(expense)
        }
    }
    fun deleteExpenseByBankId(
        bankId: Int,
        fixedOrNo: String,
        expenseDate: String,
        spentOrReceived: String?,
        bank: Bank
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentDate = LocalDate.now()
            val currentDateFormatted = currentDate.format(dateFormatter)

            // Converte a data da despesa para LocalDate
            val parsedExpenseDate = LocalDate.parse(expenseDate, dateFormatter)

            // Captura todas as datas de fechamento de bancos
            val allBankClosureDates = repository.getAllBankClosureDates()
            val closures = allBankClosureDates.filter {
                val date = it.toLocalDateOrNull(dateFormatter)
                date?.month == currentDate.month
            }

            // Se estamos na última data de fechamento, exclui despesas marcadas para deletar

            // Verifica se a data de despesa é igual à data atual
            if ( parsedExpenseDate.isBefore(currentDate) || parsedExpenseDate.isEqual(currentDate) ) {
                // Atualiza a data da despesa para o mês seguinte
                val newDate = parsedExpenseDate.plusMonths(1).format(dateFormatter)
                Log.d("DATA", "Atualizando data para: $newDate")

                repository.updateExpenseDate(bank.id, newDate)
                val updatedBank = repository.getBankById(bank.id)
                Log.d("Updated Bank", "${updatedBank?.date}, ${updatedBank?.name}")

                // Se a despesa for "Variable", marcar como pronta para exclusão
                if (fixedOrNo == "Variable") {
                    val totalSpent = repositoryExpense.getTotalSpentForBank(bankId) ?: 0.0
                    val totalReceived = repositoryExpense.getTotalReceivedForBank(bankId) ?: 0.0
                    val sum = totalSpent - totalReceived

                    // Atualiza o balance do banco de acordo com a despesa/recebido
                    val newBalance = when (spentOrReceived) {
                        "Spent" -> updatedBank?.balance?.plus(sum) ?: 0.0
                        "Received" -> updatedBank?.balance?.plus(sum) ?: 0.0
                        else -> updatedBank?.balance ?: 0.0
                    }
                    repositoryExpense.markExpensesReadyForDeletion(bankId)
                    updatedBank?.let {
                        repository.updateBank(it.copy(balance = newBalance))
                    }
                }
            }
            if (closures.max() == currentDateFormatted) {
                val totalSpentBack = repositoryExpense.getSpent() ?: 0.0
                val totalReceivedBack = repositoryExpense.getReceived() ?: 0.0
                val sum = totalSpentBack - totalReceivedBack

                Log.d("ULTIMA DATA AMIGO", "PEGA ESSES VALORES $totalReceivedBack, $totalSpentBack e $sum")

                val monthly = MonthlyExpense(monthlyExpense = sum, monthly = LocalDate.now().month.toString().take(3), yearExpense = LocalDate.now().year)
                repositoryMonthly.insertMonthlyExpense(monthly)
                repositoryMonthly.removeDuplicateMonthlyExpenses()
                repositoryExpense.deleteExpensesReadyForDeletion()
                repositoryExpense.deleteExpenseByBankId(bankId, "Variable")
                val count = repositoryMonthly.getItemCount()

                if(count > 6){
                    repositoryMonthly.deleteOldestItem()
                }
            }
        }
    }
    fun getAllMonthlyExpense() : LiveData<List<MonthlyExpense?>>{
        return repositoryMonthly.getAllMonthlyExpense()
    }

}