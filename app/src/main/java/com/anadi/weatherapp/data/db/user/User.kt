package com.anadi.weatherapp.data.db.user

import android.net.Uri
import androidx.room.*
import org.joda.time.*

const val NO_ID = -1

@Entity(tableName = "user")
data class User(
        @PrimaryKey(autoGenerate = true)
        val id: Int,

        @ColumnInfo(name = "firebase_uid")
        val firebaseUid: String,

        @ColumnInfo(name = "full_name")
        val fullName: String,

        @ColumnInfo(name = "nick_name")
        val nickName: String,

        @ColumnInfo(name = "phone_number")
        val phoneNumber: String,

        @ColumnInfo(name = "color_id")
        val colorId: UserColors = UserColors.ORANGE,

        @ColumnInfo(name = "photo_url")
        val photoUrl: Uri = Uri.parse(""),

        @ColumnInfo(name = "avatar_id")
        val avatarId: Int = NO_ID,

        @ColumnInfo(name = "rank")
        val rank: Int = 1,

        @ColumnInfo(name = "message_count")
        val messageCount: Int = 0
) {
        fun toMap() = mapOf(
                "fullName" to fullName,
                "nickName" to nickName,
                "phoneNumber" to phoneNumber,
                "colorId" to colorId,
                "photoUrl" to photoUrl.toString(),
                "avatarId" to avatarId,
                "rank" to rank,
                "messageCount" to messageCount
        )
}