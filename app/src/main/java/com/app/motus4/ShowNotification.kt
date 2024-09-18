package com.app.motus4

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.app.motus4.View.MainActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.concurrent.TimeUnit

fun showNotification(context: Context, title: String, message: String) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    // Crie um PendingIntent com o Intent criado acima
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Adicione FLAG_IMMUTABLE se targeting Android 12+
    )
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationId = 1
    val channelId = "due_date_channel"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId, "Due Date Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.nubank) // Substitua pelo seu ícone
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        .build()

    notificationManager.notify(notificationId, notification)
}
fun showNotificationAlert(context: Context) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    // Crie um PendingIntent com o Intent criado acima
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Adicione FLAG_IMMUTABLE se targeting Android 12+
    )
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationId = 1
    val channelId = "due_date_channel"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId, "Due Date Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.nubank) // Substitua pelo seu ícone
        .setContentTitle("Já anotou seus gastos/recebidos hoje??")
        .setContentText("Caso não, anote agora!!")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .build()

    notificationManager.notify(notificationId, notification)
}
class Alert(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        try {
            showNotificationAlert(applicationContext)
        } catch (e: DateTimeParseException) {
            Log.d("NotifyAlert", "Está com mal funcionamento o notify")
        }

        return Result.success()
    }
}

class DueDateWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val bankName = inputData.getString("bankName") ?: "Seu banco"
        val dueDate = inputData.getString("dueDate") // Data de vencimento

        // Defina o formatador para o formato da data
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val currentDate = LocalDate.now()

        try {
            // Analise a data de vencimento usando o formatador
            val dueDateLocal = LocalDate.parse(dueDate, formatter)

            // Verifique se a data de vencimento é amanhã
            if (dueDateLocal.minusDays(1).isEqual(currentDate)) {
                Log.d("DueDateWorker", "Data de vencimento valida: $bankName")
                showNotification(
                    applicationContext,
                    "Lembrete de Vencimento",
                    "Seu banco $bankName vence amanhã!"
                )
            }
        } catch (e: DateTimeParseException) {
            // Log a exception or handle the parsing error
            Log.e("DueDateWorker", "Data de vencimento inválida: $dueDate", e)
        }

        return Result.success()
    }
}

fun scheduleDueDateReminder(bankName: String, dueDate: String, context: Context) {
    val workManager = WorkManager.getInstance(context)

    val data = workDataOf(
        "bankName" to bankName,
        "dueDate" to dueDate
    )

    val request = PeriodicWorkRequestBuilder<DueDateWorker>(1, TimeUnit.DAYS)
        .setInputData(data)
        .build()

    workManager.enqueueUniquePeriodicWork(
        "due_date_reminder_$bankName",
        ExistingPeriodicWorkPolicy.REPLACE,
        request
    )
}
fun scheduleAlertReminder(context: Context) {
    val workManager = WorkManager.getInstance(context)


    val request = PeriodicWorkRequestBuilder<Alert>(12, TimeUnit.HOURS)
        .build()

    workManager.enqueueUniquePeriodicWork(
        "alert_reminder",
        ExistingPeriodicWorkPolicy.REPLACE,
        request
    )
}