package pl.matyjasiakm.volunteermanager.data

import android.location.Location
import com.google.type.LatLng
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import pl.matyjasiakm.volunteermanager.data.authentication.IAuthentiactionRepo
import pl.matyjasiakm.volunteermanager.data.dao.*
import pl.matyjasiakm.volunteermanager.data.database.IDatabaseRepo
import pl.matyjasiakm.volunteermanager.data.rest.WeatherService
import javax.inject.Inject

class DataManagerImpl @Inject constructor(
    val auth: IAuthentiactionRepo,
    val database: IDatabaseRepo,
    val weatherService: WeatherService
) : IDataManager {
    override fun getCoordinatorWithEmail(email: String): Single<Coordinator> {
        return database.getCoordinatorWithEmail(email)
    }

    override fun getAllInfoList(): Single<List<WallMessage>> {
        return database.getAllInfoList()
    }

    override fun getAllCoordinators(): Single<List<Coordinator>> {
        return database.getAllCoordinators()
    }

    override fun addMessage(message: WallMessage): Completable {
        return database.addMessage(message)
    }

    override fun getLevels(): Single<List<Level>> {
        return database.getLevels()
    }

    override fun getAllVolunteers(): Single<List<Volunteer>> {
        return database.getAllVolunteers()
    }

    override fun addVolunteer(volunteer: Volunteer): Completable {
        return database.addVolunteer(volunteer)
    }

    override fun updateMyLocation(email: String, location: MyLatLng): Completable {
        return database.updateMyLocation(email, location)
    }

    override fun getVolunteerWithEmail(email: String): Single<Volunteer> {
        return database.getVolunteerWithEmail(email)
    }

    override fun addDuty(duty: Duty): Completable {
        return database.addDuty(duty)
    }

    override fun getDutiesFromVolunteer(email: String): Single<List<Duty>> {
        return database.getDutiesFromVolunteer(email)
    }


    override fun getVolunteerFromPhone(phone: String): Single<Volunteer> {
        return database.getVolunteerFromPhone(phone)
    }

    override fun getDutiesFromDate(date: OwnDateTime): Single<List<Duty>> {
        return database.getDutiesFromDate(date)
    }

    override fun deleteDutyWithId(id: String): Completable {
        return database.deleteDutyWithId(id)
    }

    override fun getLocationKey(
        apiKey: String,
        q: String
    ): Single<WeatherLocation> {
        return weatherService.getLocationKey(apiKey, q)
    }

    override fun get5DayForecast(
        locationKey: String,
        apikey: String
    ): Observable<DailyForecastList> {
        return weatherService.get5DayForecast(locationKey, apikey)
    }


    override fun isSigned(): Single<Boolean> {
        return auth.isSigned()
    }

    override fun getSignedUserEmail(): Single<String> {
        return auth.getSignedUserEmail()
    }

    override fun signInUser(email: String, password: String): Completable {
        return auth.signInUser(email, password)
    }

    override fun logOutUser() {
        auth.logOutUser()
    }
}