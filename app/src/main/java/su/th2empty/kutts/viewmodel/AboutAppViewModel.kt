package su.th2empty.kutts.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import su.th2empty.kutts.BuildConfig
import su.th2empty.kutts.R

class AboutAppViewModel(application: Application) : AndroidViewModel(application) {

    val applicationVersionValue = MutableLiveData<String>()
    val osVersionValue = MutableLiveData<String>()
    val deviceName = MutableLiveData<String>()
    private val resources: Resources = application.resources
    val showCopiedMessage = MutableLiveData<Boolean>()

    init {
        loadVersionInfo()
    }

    /**
     * Loads the version information of the application, operating system, and device.
     * Updates the corresponding LiveData objects with the formatted version information.
     */
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
        return BuildConfig.VERSION_CODE to BuildConfig.VERSION_NAME
    }

    fun copyToClipboard() {
        val aboutText = getAboutText()
        copyTextToClipboard(aboutText)
        showCopiedMessage.value = true
    }

    private fun getAboutText(): String {
        return "${applicationVersionValue.value}\n${osVersionValue.value}\n${deviceName.value}"
    }

    private fun copyTextToClipboard(text: String) {
        val clipboardManager = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clipData = ClipData.newPlainText("label", text)
        clipboardManager?.setPrimaryClip(clipData)
    }
}