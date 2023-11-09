package cl.acorrea.moveit

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat


class MainMenuActivity : AppCompatActivity()
{
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
            val intentPref = Intent(this, SettingsActivity::class.java)
            startActivity(intentPref)
        }

        var gameButton = findViewById<ImageView>(R.id.mainPreferenceClickeable)
        gameButton.setOnClickListener{
            val intentPref = Intent(this, GameActivity::class.java)
            startActivity(intentPref)
        }

    }
}