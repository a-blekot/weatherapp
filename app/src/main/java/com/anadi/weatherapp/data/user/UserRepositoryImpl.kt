package com.anadi.weatherapp.data.user

import com.anadi.weatherapp.data.db.user.*
import com.anadi.weatherapp.domain.user.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val fbUsers: DatabaseReference,
        private val userDao: UserDao
        ) : UserRepository {

    override var activeUser: User? = null

    override suspend fun activate(fbUser: FirebaseUser, userName: String?) {
        val existingUser = fetch(fbUser.uid)
        if (existingUser != null) return

        with(fbUser) {
            val newUser = User(
                    id = 0,
                    firebaseUid = uid,
                    fullName = userName ?: "userName",
                    nickName = displayName ?: "nickName",
                    phoneNumber = phoneNumber ?: "+38-108-0-108-108",
            )
            add(newUser)
            activeUser = newUser

            val newKey = fbUsers.push().key!!
            fbUsers.child(newKey).setValue(newUser.toMap())
        }
    }

    override suspend fun fetch(fbUid: String) = userDao.fetch(fbUid)

    override suspend fun fetch(id: Int) = userDao.fetch(id)

    override suspend fun fetchAll() = userDao.fetchAll()

    override suspend fun add(obj: User): User {
        userDao.insert(obj)
        return userDao.last()!!
    }

    override suspend fun delete(obj: User) = userDao.delete(obj)

    override suspend fun update(obj: User) = userDao.update(obj)
}