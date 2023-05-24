package su.th2empty.kutts

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.th2empty.kutts.repository.KuttsDatabase
import timber.log.Timber
import timber.log.Timber.Forest.plant

class KuttsApplication : Application() {

    companion object {
        lateinit var instance: KuttsApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        CoroutineScope(Dispatchers.IO).launch {
            KuttsDatabase.copyDatabaseFromAssets(instance)
        }
    }
}