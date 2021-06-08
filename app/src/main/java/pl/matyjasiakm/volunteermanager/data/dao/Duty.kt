package pl.matyjasiakm.volunteermanager.data.dao

data class Duty(
    var id: String,
    val email: String,
    var info: String,
    var start: OwnDateTime,
    var end: OwnDateTime
) {

    constructor() : this("","", "",
        OwnDateTime(),
        OwnDateTime()
    )


}