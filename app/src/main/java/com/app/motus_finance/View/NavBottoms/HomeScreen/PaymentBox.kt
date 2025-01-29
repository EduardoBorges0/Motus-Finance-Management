package com.app.motus_finance.View.NavBottoms.HomeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.motus_finance.ViewModel.PaymentsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PaymentBox(
    modifier: Modifier,
    paymentsViewModel: PaymentsViewModel,
    navController: NavController
               ) {
    val payment = paymentsViewModel.payments.observeAsState()

    LaunchedEffect(Unit) {
        paymentsViewModel.getPayment()
    }
    Box(modifier = modifier.padding(bottom = 30.dp)) {
        OutlinedButton(
            onClick = {
                if(payment.value == null){
                    navController.navigate("addPayments")
                }
            },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(payment.value ?: "Adicionar pagamento")
        }
    }
}
