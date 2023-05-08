package su.th2empty.kutts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import su.th2empty.kutts.R

class TermsOfUseActivity : AppCompatActivity() {

    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_use)

        backButton = findViewById(R.id.back_btn)
        backButton.setOnClickListener { finish() }
    }
}