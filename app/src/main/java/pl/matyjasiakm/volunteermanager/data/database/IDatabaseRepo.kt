package pl.matyjasiakm.volunteermanager.data.database

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import pl.matyjasiakm.volunteermanager.data.dao.*

interface IDatabaseRepo {
    fun getCoordinatorWithEmail(email: String): Single<Coordinator>
    fun getAllInfoList(): Single<List<WallMessage>>
    fun getAllCoordinators(): Single<List<Coordinator>>
    fun addMessage(message: WallMessage): Completable
    fun getLevels(): Single<List<Level>>
    fun getAllVolunteers(): Single<List<Volunteer>>
    fun addVolunteer(volunteer: Volunteer): Completable
    fun updateMyLocation(email: String, location: MyLatLng): Completable
    fun getVolunteerWithEmail(email: String): Single<Volunteer>
    fun addDuty(duty: Duty): Completable
    fun getDutiesFromVolunteer(email: String): Single<List<Duty>>
    fun getVolunteerFromPhone(phone: String): Single<Volunteer>
    fun getDutiesFromDate(date: OwnDateTime): Single<List<Duty>>
    fun deleteDutyWithId(id: String): Completable
}