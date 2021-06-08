package pl.matyjasiakm.volunteermanager.di.module

import android.content.ContentResolver
import android.net.Uri
import dagger.Module
import dagger.Provides
import pl.matyjasiakm.volunteermanager.data.DataManagerImpl
import pl.matyjasiakm.volunteermanager.data.IDataManager
import pl.matyjasiakm.volunteermanager.data.authentication.FirebaseAuthData
import pl.matyjasiakm.volunteermanager.data.authentication.IAuthentiactionRepo
import pl.matyjasiakm.volunteermanager.data.database.FirebaseFirestoreRepo
import pl.matyjasiakm.volunteermanager.data.database.IDatabaseRepo
import javax.inject.Singleton

@Module
class DataManagerModule {
    @Provides
    @Singleton
    fun provideAuth(auth: FirebaseAuthData): IAuthentiactionRepo {
        return auth
    }
    @Provides
    @Singleton
    fun provideDatabase(db: FirebaseFirestoreRepo): IDatabaseRepo {
        return db
    }

    @Provides
    @Singleton
    fun provideDataManager(manager: DataManagerImpl): IDataManager {
        return manager
    }

}