package su.th2empty.kutts.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        startActivity(Intent(this@SplashScreenActivity, AboutAppActivity::class.java))
        finish()
    }
}