package pl.matyjasiakm.volunteermanager.data.authentication

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


interface IAuthentiactionRepo {
    fun isSigned(): Single<Boolean>
    fun getSignedUserEmail(): Single<String>
    fun signInUser(email: String, password: String): Completable
    fun logOutUser()
}