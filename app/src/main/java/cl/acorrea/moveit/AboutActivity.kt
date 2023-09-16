package cl.acorrea.moveit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val close = findViewById<ImageView>(R.id.aboutClose)
        close.setOnClickListener {
            finish()
        }
    }
}