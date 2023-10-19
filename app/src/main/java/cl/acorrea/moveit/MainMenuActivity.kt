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



class MainMenuActivity : AppCompatActivity() ,
    GestureDetector.OnGestureListener{

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var actionShow: TextView
    private lateinit var gestureDetector: GestureDetector
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

        mediaPlayer = MediaPlayer.create(this, R.raw.welive)

        actionShow = findViewById<TextView>(R.id.MainActionText)

        val soundB = findViewById<Button>(R.id.MainSoundButton)

        soundB.setOnClickListener {
            mediaPlayer?.start() // no need to call prepare(); create() does that for you
        }




        gestureDetector = GestureDetector(this,this)
    }

    override fun onPause() {
            super.onPause()
        mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(event!= null)
        {
            gestureDetector.onTouchEvent(event)
        }

        return super.onTouchEvent(event)
    }

    override fun onDown(p0: MotionEvent): Boolean {
        actionShow.text = "Down"
        return true;
    }

    override fun onShowPress(p0: MotionEvent) {
        actionShow.text = "ShowPress"
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        actionShow.text = "SingleTapUp"
        return true;
    }

    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        actionShow.text = "Scroll"
        return true;
    }

    override fun onLongPress(p0: MotionEvent) {
        actionShow.text = "LongPress"
    }

    override fun onFling(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        actionShow.text = "Fling"
        return true;
    }
}