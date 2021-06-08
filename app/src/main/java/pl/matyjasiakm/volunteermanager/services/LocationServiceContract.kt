package pl.matyjasiakm.volunteermanager.services

interface LocationServiceContract {
    interface Presenter{
        fun updateMyLocation(lat:Double,lng:Double)
    }
    interface Service{

    }
}