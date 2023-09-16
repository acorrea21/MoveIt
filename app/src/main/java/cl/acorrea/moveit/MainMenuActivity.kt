package cl.acorrea.moveit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        var aboutButton = findViewById<Button>(R.id.mainAboutButton)

        var preferencesButton = findViewById<Button>(R.id.mainPreferenceButton)

        aboutButton.setOnClickListener{
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }

        preferencesButton.setOnClickListener{
            val intentPref = Intent(this, PreferencesActivity::class.java)
            startActivity(intentPref)
        }

        var test = findViewById<ImageView>(R.id.mainPreferenceClickeable)
        test.setOnClickListener{
            val intentPref = Intent(this, PreferencesActivity::class.java)
            startActivity(intentPref)
        }

    }
}