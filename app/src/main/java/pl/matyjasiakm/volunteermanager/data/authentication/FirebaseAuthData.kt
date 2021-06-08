package pl.matyjasiakm.volunteermanager.data.authentication

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FirebaseAuthData @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) :
    IAuthentiactionRepo {


    override fun getSignedUserEmail(): Single<String> {
        return Single.create { emitter ->
            val user = firebaseAuth.currentUser
            user?.let {
                emitter.onSuccess(it.email)
            } ?: run {
                emitter.onError(NoSignedUserException())
            }
        }

    }

    override fun isSigned(): Single<Boolean> {
        return Single.create { emitter ->
            firebaseAuth.currentUser?.let {
                emitter.onSuccess(true)
            } ?: run { emitter.onSuccess(false) }
        }
    }

    override fun signInUser(email: String, password: String): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    emitter.onComplete()
                } else {
                    emitter.onError(it.exception)
                }
            }.addOnFailureListener{ e ->
                if(!emitter.isDisposed)
                    emitter.onError(e)
            }
        }
    }

    override fun logOutUser() {
        firebaseAuth.signOut()
    }
}