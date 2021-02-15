package com.anadi.weatherapp.domain.user

import com.anadi.weatherapp.data.db.user.User
import com.anadi.weatherapp.domain.BaseRepository
import com.google.firebase.auth.FirebaseUser

interface UserRepository : BaseRepository<User> {
    val activeUser: User?

    suspend fun activate(fbUser: FirebaseUser, userName: String?)
    suspend fun fetch(fbUid: String): User?
}
