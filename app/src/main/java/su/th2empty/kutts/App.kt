package su.th2empty.kutts

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.th2empty.kutts.repository.KuttsDatabase
import su.th2empty.kutts.repository.KuttsDatabaseHelper
import timber.log.Timber
import timber.log.Timber.Forest.plant

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        CoroutineScope(Dispatchers.IO).launch {
            val database = KuttsDatabase.getDatabase(this@App)
            val contacts = KuttsDatabaseHelper.loadContactsFromAsset(this@App, "database.db")
            database.contactsDao().insertAll(contacts)
        }
    }
}