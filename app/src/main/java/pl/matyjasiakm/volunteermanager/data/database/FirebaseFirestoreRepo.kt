package pl.matyjasiakm.volunteermanager.data.database

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import pl.matyjasiakm.volunteermanager.data.dao.*
import java.util.*
import javax.inject.Inject

class FirebaseFirestoreRepo @Inject constructor(val db: FirebaseFirestore) : IDatabaseRepo {
    override fun getCoordinatorWithEmail(email: String): Single<Coordinator> {
        return Single.create { emitter ->
            db.collection("coordinators").document(email).get().addOnSuccessListener {
                val coordinator = it.toObject(Coordinator::class.java)
                emitter.onSuccess(coordinator)
            }

        }
    }

    override fun getAllInfoList(): Single<List<WallMessage>> {
        return Single.create { emitter ->
            val list = LinkedList<WallMessage>()
            db.collection("messages").orderBy("created", Query.Direction.DESCENDING).get()
                .addOnSuccessListener {
                    for (doc in it) {
                        val message = doc.toObject(WallMessage::class.java)
                        list.addLast(message)
                    }
                    emitter.onSuccess(list)
                }
        }
    }

    override fun getAllCoordinators(): Single<List<Coordinator>> {
        return Single.create { emitter ->
            val list = LinkedList<Coordinator>()
            db.collection("coordinators").get().addOnSuccessListener {
                for (c in it) {
                    val coordinator = c.toObject(Coordinator::class.java)
                    list.add(coordinator)
                }
                emitter.onSuccess(list)
            }
        }
    }

    override fun addMessage(message: WallMessage): Completable {
        return Completable.create { emitter ->
            db.collection("messages").add(message).addOnSuccessListener {
                emitter.onComplete()
            }
        }

    }

    override fun getLevels(): Single<List<Level>> {
        return Single.create { emitter ->
            val levels = LinkedList<Level>()
            db.collection("levels").get().addOnSuccessListener {
                for (l in it) {
                    val level = l.toObject(Level::class.java)
                    levels.add(level)
                }
                levels.sortBy { t -> t.levelVal }
                levels.reverse()
                emitter.onSuccess(levels)
            }
        }
    }

    override fun getAllVolunteers(): Single<List<Volunteer>> {
        return Single.create { emitter ->
            db.collection("volunteers").get().addOnSuccessListener {
                val list = LinkedList<Volunteer>()
                for (v in it) {
                    val volunteer = v.toObject(Volunteer::class.java)
                    list.add(volunteer)
                }
                emitter.onSuccess(list)
            }

        }
    }

    override fun addVolunteer(volunteer: Volunteer): Completable {
        return Completable.create { emitter ->
            db.collection("volunteers").add(volunteer).addOnSuccessListener {
                emitter.onComplete()
            }
        }
    }

    override fun updateMyLocation(email: String, location: MyLatLng): Completable {
        return Completable.create { emitter ->
            db.collection("coordinators").document(email).update("location", location)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
        }
    }

    override fun getVolunteerWithEmail(email: String): Single<Volunteer> {
        return Single.create { emitter ->
            db.collection("volunteers").whereEqualTo("email", email).get().addOnSuccessListener {
                for (t in it) {
                    val volunteer = t.toObject(Volunteer::class.java)
                    emitter.onSuccess(volunteer)
                    break
                }
            }
        }
    }

    override fun addDuty(duty: Duty): Completable {
        return Completable.create { emitter ->
            db.collection("duties").add(duty).addOnSuccessListener {
                it.update("id", it.id)
                    .addOnSuccessListener {
                        emitter.onComplete()
                    }
                    .addOnFailureListener { e ->
                        emitter.onError(e)
                    }
            }
                .addOnFailureListener { e ->
                    emitter.onError(e)
                }
        }
    }

    override fun getDutiesFromVolunteer(email: String): Single<List<Duty>> {
        return Single.create { emitter ->
            db.collection("duties").whereEqualTo("email", email).get().addOnSuccessListener { t ->
                val list = mutableListOf<Duty>()
                for (d in t) {
                    val duty = d.toObject(Duty::class.java)
                    list.add(duty)
                }
                emitter.onSuccess(list)
            }
        }
    }


    override fun getVolunteerFromPhone(phone: String): Single<Volunteer> {
        return Single.create { emitter ->
            db.collection("volunteers").whereEqualTo("phoneNumber", phone).get()
                .addOnSuccessListener {
                    if (it.documents.size == 0) {
                        emitter.onSuccess(Volunteer())

                    } else {
                        val v = it.documents[0].toObject(Volunteer::class.java)
                        emitter.onSuccess(v)
                    }
                }
        }
    }

    override fun getDutiesFromDate(date: OwnDateTime): Single<List<Duty>> {
        return Single.create { emitter ->
            db.collection("duties")
                .whereGreaterThan(FieldPath.of("end", "dateTime"), date.toStringDataYearFirst())
                .get().addOnSuccessListener {
                    val q = it.toObjects(Duty::class.java) as List<Duty>
                    emitter.onSuccess(q)
                }
        }
    }

    override fun deleteDutyWithId(id: String): Completable {
        return Completable.create { emitter ->
            db.collection("duties").document(id).delete().addOnSuccessListener {
                emitter.onComplete()
            }
        }
    }
}
