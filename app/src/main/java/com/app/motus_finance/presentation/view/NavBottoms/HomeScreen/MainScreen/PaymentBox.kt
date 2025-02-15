package com.app.motus_finance.presentation.view.NavBottoms.HomeScreen.MainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.motus_finance.presentation.UtilityClass.UtilityClass
import com.app.motus_finance.presentation.view.UtilsComposable.AlertDialogComposable
import com.app.motus_finance.presentation.viewmodel.PaymentsViewModel

@Composable
fun PaymentBox(
    modifier: Modifier,
    paymentsViewModel: PaymentsViewModel,
    navController: NavController
               ) {
    val payment = paymentsViewModel.getPayment().observeAsState()
    var showDialog = paymentsViewModel.alertDialog.observeAsState(false)

    LaunchedEffect(Unit) {
        paymentsViewModel.getPayment()
    }
    Box(modifier = modifier.padding(bottom = 30.dp)) {
        OutlinedButton(
            onClick = {
                if(payment.value == null){
                    navController.navigate("insertPayment")
                }else{
                  paymentsViewModel.setAlertDialog(true)
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(150.dp).height(if(payment.value == null ) 55.dp else 50.dp)
        ) {
            Text(payment.value?.let { UtilityClass.currencyFormat(it) } ?: "Quanto você pode gastar?",
                fontSize = 17.sp)
        }
    }
    AlertDialogComposable(
        showDialog = showDialog.value,
        onDismiss = { paymentsViewModel.setAlertDialog(false) },
        onConfirm = {
            paymentsViewModel.deletePayment()
            paymentsViewModel.setAlertDialog(false)
        },
        "Você tem certeza que deseja deletar o pagamento?"
    )
}
