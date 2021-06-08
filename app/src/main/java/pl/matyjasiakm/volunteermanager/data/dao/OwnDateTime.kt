package pl.matyjasiakm.volunteermanager.data.dao

data class OwnDateTime(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int
) {

    val dateTime = "${toStringDataYearFirst()} ${toStringTime()}"

    constructor() : this(0, 0, 0, 0, 0)

    fun toStringData(): String {
        return "${day.toString().padStart(2, '0')}.${month.toString().padStart(2, '0')}.$year"
    }

    fun toStringDataYearFirst(): String {
        return "$year/${month.toString().padStart(2, '0')}/${day.toString().padStart(2, '0')}"
    }

    fun toStringTime(): String {
        return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
    }
}