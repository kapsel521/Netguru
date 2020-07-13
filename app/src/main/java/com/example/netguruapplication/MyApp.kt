package com.example.netguruapplication

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

open class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val configuration = RealmConfiguration.Builder()
            .name("ShopList.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()

        Realm.setDefaultConfiguration(configuration)

        archiveConfiguration()
    }

    fun archiveConfiguration(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name("ArchivedList.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
    }
}