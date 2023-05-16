package su.th2empty.kutts.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import su.th2empty.kutts.BuildConfig
import su.th2empty.kutts.R

class AboutAppViewModel(application: Application) : AndroidViewModel(application) {

    val applicationVersionValue = MutableLiveData<String>()
    val osVersionValue = MutableLiveData<String>()
    val deviceName = MutableLiveData<String>()
    val resources: Resources = getApplication<Application>().resources

    init {
        loadVersionInfo()
    }

    private fun loadVersionInfo() {
        val (versionCode, versionName) = getVersionInfo()
        applicationVersionValue.value =
            resources.getString(R.string.st_format_application_version)
                .format("$versionName ($versionCode)")

        osVersionValue.value = resources.getString(R.string.st_format_os_version)
            .format("${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")

        deviceName.value = resources.getString(R.string.st_format_device_name)
            .format("${Build.BRAND} ${Build.MODEL}")
    }

    private fun getVersionInfo(): Pair<Int, String> {
        return Pair(BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME)
    }

    fun copyToClipboard() {
        copyTextToClipboard(buildAboutText())
    }

    private fun buildAboutText(): String {
        return "${applicationVersionValue.value}\n${osVersionValue.value}\n${deviceName.value}"
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