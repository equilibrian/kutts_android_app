package su.th2empty.kutts

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var wtf: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wtf = findViewById(R.id.wtf)
        val versionCode = @Suppress("DEPRECATION") applicationContext.packageManager
            .getPackageInfo(applicationContext.packageName, 0).versionCode
        val versionName = @Suppress("DEPRECATION") applicationContext.packageManager
            .getPackageInfo(applicationContext.packageName, 0).versionName

        wtf.text = "Version code: $versionCode | version name: $versionName"
    }
}