package su.th2empty.kutts

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.th2empty.kutts.repository.KuttsDatabase
import su.th2empty.kutts.utils.AppPreferences
import su.th2empty.kutts.utils.VERSION_CODE_PARAM
import su.th2empty.kutts.utils.VERSION_CODE_PARAM_UNSET
import timber.log.Timber
import timber.log.Timber.Forest.plant
import java.lang.Exception

class KuttsApplication : Application() {

    companion object {
        lateinit var instance: KuttsApplication
    }

    private fun setupConfigs() {
        val prefs = AppPreferences(instance)

        if (prefs.getParam(VERSION_CODE_PARAM, VERSION_CODE_PARAM_UNSET) == VERSION_CODE_PARAM_UNSET) {
            prefs.putParam(VERSION_CODE_PARAM, BuildConfig.VERSION_CODE)
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                KuttsDatabase.copyDatabaseFromAssets(instance)
            } catch (ex: Exception) {
                Timber.wtf(ex)
            }
        }

        setupConfigs()
    }
}