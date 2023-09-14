package cl.acorrea.moveit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        var aboutButton = findViewById<Button>(R.id.aboutButton)

        var preferencesButton = findViewById<Button>(R.id.preferencesButton)

        aboutButton.setOnClickListener{
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }

        preferencesButton.setOnClickListener{
            val intentPref = Intent(this, PreferencesActivity::class.java)
            startActivity(intentPref)
        }

    }
}