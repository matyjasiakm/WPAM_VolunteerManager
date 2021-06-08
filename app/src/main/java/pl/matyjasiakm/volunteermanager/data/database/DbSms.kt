package pl.matyjasiakm.volunteermanager.data.database

import android.content.ContentResolver
import android.content.Context
import javax.inject.Inject

class DbSms @Inject constructor(context: Context) : IDbSms {
    var contenResolver: ContentResolver = context.contentResolver



}