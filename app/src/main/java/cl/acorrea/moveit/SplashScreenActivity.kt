package cl.acorrea.moveit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import cl.acorrea.moveit.Object.Utilities

class SplashScreenActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(Utilities.GetPlayIntro(this))
        {
            SetAnimation()
        }
        else
        {
            GoToNext()
        }
    }

    fun GoToNext()
    {
        val intent = Intent(this@SplashScreenActivity, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun SetAnimation()
    {
        val rotateAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)

        val icon = findViewById<ImageView>(R.id.splashAppIcon)

        rotateAnim.setAnimationListener(
            object : Animation.AnimationListener
            {
                override fun onAnimationStart(animation: Animation?)
                {
                    // Animation started
                }

                override fun onAnimationEnd(animation: Animation?)
                {
                    GoToNext()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })

        icon.startAnimation(rotateAnim)
    }
}