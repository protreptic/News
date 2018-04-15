package name.peterbukhal.example.news

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

@Suppress("unused")
class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)

        val configuration =
                RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build()

        try {
            Realm.setDefaultConfiguration(configuration)
            Realm.getDefaultInstance().close()
        } catch (e: Exception) {
            Realm.deleteRealm(configuration)
        }
    }

}