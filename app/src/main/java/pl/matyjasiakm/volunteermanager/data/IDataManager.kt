package pl.matyjasiakm.volunteermanager.data

import pl.matyjasiakm.volunteermanager.data.authentication.IAuthentiactionRepo
import pl.matyjasiakm.volunteermanager.data.database.IDatabaseRepo
import pl.matyjasiakm.volunteermanager.data.rest.WeatherService

interface IDataManager : IAuthentiactionRepo, IDatabaseRepo, WeatherService{

}