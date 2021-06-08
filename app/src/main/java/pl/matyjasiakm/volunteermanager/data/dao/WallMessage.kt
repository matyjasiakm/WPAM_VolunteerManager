package pl.matyjasiakm.volunteermanager.data.dao

import java.util.*

data class WallMessage(
    var info: String = "",
    var level: Int = 0,
    val ownerEmail: String = "",
    val created: String = "",
) {
}