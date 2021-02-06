package com.anadi.weatherinfo.view.ui.notification

import android.content.Context
import android.graphics.Color
import com.anadi.weatherinfo.R
import com.kirich1409.androidnotificationdsl.contentText
import com.kirich1409.androidnotificationdsl.contentTitle
import com.kirich1409.androidnotificationdsl.group.NotificationsGroup
import com.kirich1409.androidnotificationdsl.group.notificationsGroup

private const val CHANNEL_DEFAULT = "CHANNEL_DEFAULT"
private const val NOTIFICATION_CHANNEL_LOW = "NOTIFICATION_CHANNEL_LOW"

fun buildNotificationsGroup(context: Context): NotificationsGroup {
    return notificationsGroup(context, groupKey = GROUP_KEY, channelId = CHANNEL_DEFAULT) {
        summary(SUMMARY_NOTIFICATION_ID, smallIcon = R.drawable.ic_update) {
            contentTitle(R.string.notification_summary_title)
            contentText(R.string.notification_summary_text)
//            color = Color.parseColor("#5AAC37")
//            colorized = true
        }

//        notification(
//                NOTIFICATION_2_ID,
//                smallIcon = R.drawable.ic_update,
//                channelId = NOTIFICATION_CHANNEL_LOW
//        ) {
//            contentTitle = "Привет Android Broadcast"
//            whenTime = System.currentTimeMillis() - 5_000
//            color = Color.parseColor("#5AAC37")
//            colorized = true
//        }
//
//        notification(
//                NOTIFICATION_1_ID,
//                smallIcon = R.drawable.ic_update,
//                channelId = NOTIFICATION_CHANNEL_LOW
//        ) {
//            contentTitle = "Mobius 2020 Online"
//            whenTime = System.currentTimeMillis() - 1_000
//            color = Color.parseColor("#5AAC37")
//            colorized = true
//        }
    }
}

private const val NOTIFICATION_2_ID = 3
private const val NOTIFICATION_1_ID = 2
private const val SUMMARY_NOTIFICATION_ID = 1
private const val GROUP_KEY = "group1"