package pl.matyjasiakm.volunteermanager.data.dao

class Volunteer constructor(
    var FirstName: String = "",
    var LastName: String = "",
    var PhoneNumber: String = "",
    var Group: String = "",
    var Email: String = ""
) {
    val FullName
        get() = FirstName + " " + LastName
}