package com.thousand.bus.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.thousand.bus.R
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.presentation.activity.MainActivity
import com.thousand.bus.main.presentation.customer.ticket.TicketCustomerFragment
import com.thousand.bus.main.presentation.driver.passenger.PassengerDriverFragment
import java.util.*

class PushNotificationService : FirebaseMessagingService() {

    private val tag = "FirebaseMessagingService"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println("$tag token --> $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        try {
            showNotification(remoteMessage.data["title"], remoteMessage.data["body"], remoteMessage.data["type"], remoteMessage.data["car_id"], remoteMessage.data["carId"] )
            Log.d("hello", "type notif + ${remoteMessage.data["type"]}")
        } catch (e: Exception) {
            println("$tag error -->${e.localizedMessage}")
        }
    }

    private fun showNotification(
        title: String?,
        body: String?,
        type : String?,
        carId : String?,
        carId2: String?
    ) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

        intent.putExtra("typeNotifications", type)
        intent.putExtra("carIdNotification", carId?.toInt())
        intent.putExtra("carIdFeedback", carId2?.toInt())

        val notificationId = Random().nextInt(60000)

        val pendingIntent = PendingIntent.getActivity(
            this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_id)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannels(channelId, channelName, notificationManager)
        }

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(soundUri)
        notificationBuilder.setContentIntent(pendingIntent)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupNotificationChannels(
        channelId: String,
        channelName: String,
        notificationManager: NotificationManager
    ) {
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.lightColor = Color.GREEN
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)
    }
}