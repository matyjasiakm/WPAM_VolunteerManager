package pl.matyjasiakm.volunteermanager.data.dao

data class Coordinator(
    var name: String = "",
    var surname: String = "",
    var phoneNumber: String = "",
    var email: String = "",
    var location: MyLatLng?=null,
    var coordinatedGroups: List<String> = arrayListOf()
) {
    val FullName: String
        get() = name + " " + surname
}
