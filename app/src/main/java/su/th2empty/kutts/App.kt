package su.th2empty.kutts

import android.app.Application
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import su.th2empty.kutts.di.AppComponent
import su.th2empty.kutts.di.AppModule
import su.th2empty.kutts.di.DaggerAppComponent
import su.th2empty.kutts.repository.KuttsDatabase
import su.th2empty.kutts.repository.KuttsDatabaseHelper
import timber.log.Timber
import timber.log.Timber.Forest.plant

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        GlobalScope.launch {
            val database = KuttsDatabase.getDatabase(this@App)
            val contacts = KuttsDatabaseHelper.loadContactsFromAsset(this@App, "database.db")
            database.contactsDao().insertAll(contacts)
        }

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}