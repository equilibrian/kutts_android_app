package su.th2empty.kutts

import android.app.Application
import su.th2empty.kutts.repository.KuttsDatabase
import su.th2empty.kutts.utils.AppPreferences
import su.th2empty.kutts.utils.VERSION_CODE_PARAM
import su.th2empty.kutts.utils.VERSION_CODE_PARAM_UNSET
import timber.log.Timber
import timber.log.Timber.Forest.plant
import java.io.File

class KuttsApplication : Application() {

    companion object {
        lateinit var instance: KuttsApplication
    }

    private fun setupConfigs() {
        val prefs = AppPreferences(instance)

        if (prefs.getParam(VERSION_CODE_PARAM, VERSION_CODE_PARAM_UNSET) == VERSION_CODE_PARAM_UNSET) {
            Timber.i("Version parameter unset. Setting version parameter...")
            prefs.putParam(VERSION_CODE_PARAM, BuildConfig.VERSION_CODE)
        }
    }

    private fun validateDatabase() {
        Timber.tag(KuttsDatabase.TAG).i("Validation database...")
        val databasePath = instance.getDatabasePath(KuttsDatabase.DATABASE_NAME).path

        AppPreferences(instance).let { prefs ->
            val versionCode = prefs.getParam(VERSION_CODE_PARAM, VERSION_CODE_PARAM_UNSET)
            if (versionCode == BuildConfig.VERSION_CODE && File(databasePath).exists()) {
                Timber.tag(KuttsDatabase.TAG).i("Validation passed")
            } else {
                Timber.tag(KuttsDatabase.TAG).i("Validation failed. Copying new database...")
                KuttsDatabase.copyDatabaseFromAssets(instance, databasePath)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        setupConfigs()
        validateDatabase()
    }
}