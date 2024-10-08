package com.app.motus4.View

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.motus2.View.AddYourBankComposable
import com.app.motus2.View.ChangeLanguageContent
import com.app.motus2.View.CreditOrDebitComposable
import com.app.motus2.View.ExpenseClassificationBank
import com.app.motus4.Models.RepositoryMonthly
import com.app.simplemoney.Models.Repository
import com.app.simplemoney.Models.Room.Bank
import com.app.simplemoney.Models.Room.DatabaseProvider
import com.app.motus4.ViewModels.BankViewModel.BankViewModelFactory
import com.app.simplemoney.ui.theme.DarkBlue
import com.app.simplemoney.ui.theme.SimpleMoneyTheme
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.simplemoney8.Models.RepositoryExpense
import com.app.motus4.R
import com.app.motus4.ViewModels.ExpenseViewModel.ExpenseViewModel
import com.app.motus4.ViewModels.ExpenseViewModel.ExpenseViewModelFactory
import com.app.simplemoney8.Models.RepositoryLanguage
import com.app.simplemoney8.View.Expenses.ExpenseClassificationComposable
import com.app.simplemoney8.View.Expenses.ExpensesComposable
import com.app.simplemoney8.View.NavBottom.HomeScreenComposable
import com.app.simplemoney8.customFontFamily
import com.app.motus4.setAppLocale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : FragmentActivity() {
    private lateinit var viewModel: BankViewModel
    private lateinit var expenseViewModel: ExpenseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        CoroutineScope(Dispatchers.Main).launch {
            val language = viewModel.updateLanguage()
            Log.d("language", "Retorne $language")
            setAppLocale(this@MainActivity, language.toString())
        }
    expenseViewModel.showNotify(applicationContext)
    expenseViewModel.showNotifyAlert(applicationContext)
        setContent {
            SimpleMoneyTheme {
                Surface {
                    SetupNavController()
                }
            }
        }
    }

    private fun initializeViewModel() {
        val dao = DatabaseProvider.getDao(application)
        val daoExpense = DatabaseProvider.getDaoExpense(application)
        val daoLanguage = DatabaseProvider.getDaoLanguage(application)
        val daoMonthly = DatabaseProvider.getDaoMonthly(application)

        val repository = Repository(dao)
        val expenseRepository = RepositoryExpense(daoExpense)
        val monthlyRepository = RepositoryMonthly(daoMonthly)
        val repositoryLanguage = RepositoryLanguage(daoLanguage)

        val factory = BankViewModelFactory(application, repository, repositoryLanguage)
        val factoryExpense = ExpenseViewModelFactory(application, repository, expenseRepository, monthlyRepository)

        viewModel = ViewModelProvider(this, factory)[BankViewModel::class.java]
        expenseViewModel = ViewModelProvider(this, factoryExpense)[ExpenseViewModel::class.java]
      }

    @Composable
    fun SetupNavController() {
        val navController = rememberNavController()
        val context = LocalContext.current as Activity
        NavHost(navController = navController, startDestination = "splash") {
            composable("main") { SimpleMoneyEnter(viewModel = expenseViewModel, navController = navController) }
            composable("expenseClassificationNavBottom?classification={classification}",
                arguments = listOf(navArgument("classification") { type = NavType.StringType })
            ) {
                val classification = it.arguments?.getString("classification")
                ExpenseClassificationBank(viewModel = viewModel,
                    classification = classification,
                    navController = navController,
                    expenseViewModel = expenseViewModel
                )
            }
            composable("changeLanguage") { ChangeLanguageContent(
                navController = navController,
                activity = context,
                viewModel = viewModel
            ) }
            composable(
                "expenseClassification?bankId={bankId}&expense={expense}&expenseValue={expenseValue}&selectedOptionSpentOrReceived={selectedOptionSpentOrReceived}&selectedExpenseType={selectedExpenseType}",
                arguments = listOf(
                    navArgument("bankId") { type = NavType.IntType },
                    navArgument("expense") { type = NavType.StringType },
                    navArgument("expenseValue") { type = NavType.StringType },
                    navArgument("selectedOptionSpentOrReceived") { type = NavType.StringType },
                    navArgument("selectedExpenseType") { type = NavType.StringType }
                )
            ) {
                val bankId = it.arguments?.getInt("bankId") ?: 0
                val expense = it.arguments?.getString("expense") ?: ""
                val expenseValue = it.arguments?.getString("expenseValue") ?: ""
                val selectedOptionSpentOrReceived = it.arguments?.getString("selectedOptionSpentOrReceived") ?: ""
                val selectedExpenseType = it.arguments?.getString("selectedExpenseType") ?: ""
                val bank = remember { mutableStateOf<Bank?>(null) }

                LaunchedEffect(bankId) {
                    bank.value = viewModel.getBankById(bankId)
                }
                bank.value?.let { bank ->
                    ExpenseClassificationComposable(
                        navController = navController,
                        bankId = bankId,
                        expense = expense,
                        expenseValue = expenseValue.toDouble(),
                        selectedOptionSpentOrReceived = selectedOptionSpentOrReceived,
                        selectedExpenseType = selectedExpenseType,
                        viewModel = viewModel,
                        bank = bank,
                        expenseViewModel = expenseViewModel
                    )
                }
            }
            composable("home") {
                HomeScreenComposable(viewModel, navController, expenseViewModel = expenseViewModel)
            }
            composable("creditOrDebit") { CreditOrDebitComposable(navController) }
            composable(
                "addYourBank?balance={balance}&creditOrDebit={creditOrDebit}&date={date}&nameOfBank={nameOfBank}",
                arguments = listOf(
                    navArgument("balance") { type = NavType.StringType },
                    navArgument("creditOrDebit") { type = NavType.StringType },
                    navArgument("date") { type = NavType.StringType },
                    navArgument("nameOfBank") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val balance = backStackEntry.arguments?.getString("balance") ?: ""
                val creditOrDebit = backStackEntry.arguments?.getString("creditOrDebit") ?: ""
                val date = backStackEntry.arguments?.getString("date") ?: ""
                val nameOfBank = backStackEntry.arguments?.getString("nameOfBank") ?: ""

                AddYourBankComposable(
                    balance = balance.toDouble(),
                    creditOrDebit = creditOrDebit,
                    date = date,
                    nameOfBank = nameOfBank,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable("splash") {
                RevealImageAnimation(navController = navController)
            }
            composable("expense?bankId={bankId}",
                arguments = listOf(navArgument("bankId") { type = NavType.IntType })
            ) { backStackEntry ->
                val bankId = backStackEntry.arguments?.getInt("bankId") ?: 0
                val bank = remember { mutableStateOf<Bank?>(null) }

                LaunchedEffect(bankId) {
                    bank.value = viewModel.getBankById(bankId)
                }

                bank.value?.let { bank ->
                    ExpensesComposable(bankId = bankId, viewModel = viewModel, bank = bank, navController = navController ,expenseViewModel = expenseViewModel)
                }
            }
        }
    }
}


@Composable
fun SimpleMoneyEnter(navController: NavController, viewModel: ExpenseViewModel) {
    val context = LocalContext.current as MainActivity
    val executor = ContextCompat.getMainExecutor(context)
    val biometricManager = BiometricManager.from(context)


    val biometricPrompt = BiometricPrompt(context, executor, object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Log.d("BiometricAuth", "Erro de autenticação: $errString")
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            Log.d("BiometricAuth", "Autenticação bem-sucedida!")
            // Redireciona para a tela inicial
            navController.navigate("home")
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Log.d("BiometricAuth", "Falha na autenticação.")
        }
    })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Desbloqueie para usar o Motus")
        .setSubtitle("Use sua digital para acessar")
        .setAllowedAuthenticators(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        .build()

    val biometricAvailability = biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
    )

    val biometricAvailable = biometricAvailability == BiometricManager.BIOMETRIC_SUCCESS
    val credentialsAvailable = biometricAvailability != BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE &&
            biometricAvailability != BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE

    // Layout da tela de login biométrico
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(DarkBlue)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mainlogo),
                contentDescription = "Ícone de carteira",
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (biometricAvailable) {
                    biometricPrompt.authenticate(promptInfo)
                } else if (credentialsAvailable) {
                    // Inicia com credenciais do dispositivo (PIN, padrão)
                    biometricPrompt.authenticate(promptInfo)
                } else {
                    Log.d("BiometricAuth", "Nenhuma opção de autenticação disponível.")
                    navController.navigate("home")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Entrar", fontFamily = customFontFamily)
        }
    }
}
