package su.th2empty.kutts.viewmodel

import android.app.Application
import android.app.Notification
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.widget.Toast
import androidx.core.content.pm.PackageInfoCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import su.th2empty.kutts.R
import timber.log.Timber

class AboutAppViewModel(application: Application) : AndroidViewModel(application) {

    private val _applicationVersionValue = MutableLiveData<String>()
    val applicationVersionValue: LiveData<String> = _applicationVersionValue

    private val _osVersionValue = MutableLiveData<String>()
    val osVersionValue: LiveData<String> = _osVersionValue

    private val _deviceName = MutableLiveData<String>()
    val deviceName: LiveData<String> = _deviceName

    private val resources: Resources = getApplication<Application>().resources
    private val packageManager: PackageManager = application.packageManager
    private val packageName: String = application.packageName

    init {
        loadVersionInfo()
    }

    private fun loadVersionInfo() {
        val (versionCode, versionName) = getVersionInfo()
        _applicationVersionValue.value =
            resources.getString(R.string.st_format_application_version)
                .format("$versionName ($versionCode)")
        _osVersionValue.value = resources.getString(R.string.st_format_os_version)
            .format("${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
        _deviceName.value = resources.getString(R.string.st_format_device_name)
            .format("${Build.BRAND} ${Build.MODEL}")
    }

    private fun getVersionInfo(): Pair<Int, String> {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val versionCode = PackageInfoCompat.getLongVersionCode(packageInfo).toInt()
            val versionName = packageInfo.versionName
            Pair(versionCode, versionName)
        } catch (ex: PackageManager.NameNotFoundException) {
            Timber.e(ex, "Error getting package info")
            Pair(0, "")
        }
    }

    fun copyToClipboard() {
        copyTextToClipboard(buildAboutText())
    }

    private fun buildAboutText(): String {
        return "$applicationVersionValue\n$osVersionValue\n$deviceName"
    }

    private fun copyTextToClipboard(text: String) {
        val clipboard: ClipboardManager? =
            getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clip: ClipData = ClipData.newPlainText("label", text)
        clipboard?.let {
            it.setPrimaryClip(clip)
            Toast.makeText(
                getApplication(),
                resources.getString(R.string.st_copied),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}