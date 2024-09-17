package com.app.motus4.View

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.app.motus4.R
import com.app.motus4.ViewModels.BankViewModel.BankViewModel
import com.app.simplemoney.ui.theme.DarkBlue
import java.io.InputStream

@Composable
fun AddImageContent(
    nameOfBank: String?,
    viewModel: BankViewModel,
    balance: Double?,
    creditOrDebit: String?,
    date: String?,
    navController: NavController
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imagePath by remember { mutableStateOf<String?>(null) }
    val empty = "vazio"
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            imageUri = selectedUri
            val copiedImagePath = copyImageToInternalStorage(context, selectedUri)
            if (copiedImagePath != null) {
                imagePath = copiedImagePath
                Log.d("Imagem", "Imagem armazenada em $copiedImagePath")
                navController.navigate("addYourBank?balance=${balance}&creditOrDebit=${creditOrDebit}&date=${date}&nameOfBank=${nameOfBank}&img=${copiedImagePath}")
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permissão para acessar a galeria negada", Toast.LENGTH_SHORT).show()
        }
    }
    val requestPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Button(
            onClick = {
                navController.navigate("addYourBank?balance=${balance}&creditOrDebit=${creditOrDebit}&date=${date}&nameOfBank=${nameOfBank}&img=${empty}")
            },
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.TopEnd),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = DarkBlue
            )
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Return"
            )
        }
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = DarkBlue
            )
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Return"
            )
        }
        Button(
            onClick = {
                when {
                    ContextCompat.checkSelfPermission(
                        context, requestPermission
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        galleryLauncher.launch("image/*")
                    }
                    else -> {
                        permissionLauncher.launch(requestPermission)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = stringResource(id = R.string.clique_add_imagem))
        }
    }
}

fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
    val inputStream: InputStream = context.contentResolver.openInputStream(uri) ?: return null
    val fileName = "image_${System.currentTimeMillis()}.jpg"
    val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
    inputStream.copyTo(outputStream)
    inputStream.close()
    outputStream.close()
    return context.filesDir.absolutePath + "/" + fileName
}
