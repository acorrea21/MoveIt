package cl.acorrea.moveit

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import cl.acorrea.moveit.Interface.TimerCallback
import cl.acorrea.moveit.Enum.Movements
import cl.acorrea.moveit.Object.Utilities
import cl.acorrea.moveit.entities.Timer

class GameActivity : AppCompatActivity(), GestureDetector.OnGestureListener
    , GestureDetector.OnDoubleTapListener, TimerCallback{

    private val GIVE_TIMER = "give"
    private val ACTION_TIMER = "action"

    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var actionShow: TextView
    private lateinit var actionCountdownText: TextView
    private lateinit var backMusic: MediaPlayer
    private lateinit var winSound: MediaPlayer
    private lateinit var loseSound: MediaPlayer
    private var gameStart: Boolean = false
    private var lose: Boolean = false
    private lateinit var movementToDo: Movements
    private lateinit var giveTimer: Timer
    private lateinit var actionTimer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        actionShow = findViewById<TextView>(R.id.GameActionText)
        actionCountdownText = findViewById<TextView>(R.id.GameCountdownTime)
        backMusic = MediaPlayer.create(this,R.raw.background)
        winSound = MediaPlayer.create(this,R.raw.win)
        loseSound = MediaPlayer.create(this,R.raw.fail)

        giveTimer = Timer(this, GIVE_TIMER)
        actionTimer = Timer(this, ACTION_TIMER)

        gestureDetector = GestureDetectorCompat(this,this)
        gestureDetector.setOnDoubleTapListener(this)

        giveTimer.Start(5000)
    }

    override fun onPause() {
        super.onPause()
        backMusic?.pause()
        winSound?.stop()
        loseSound?.stop()
    }

    override fun onResume() {
        super.onResume()
        //TODO("ASK WHEN RESUME")
        backMusic?.start()
        //winSound?.start()
        //loseSound?.start()
    }

    override fun finish() {
        backMusic?.release()
        winSound?.release()
        loseSound?.release()
        super.finish()
    }

    private fun GiveAction()
    {
        movementToDo = Utilities.GetRandomMovement()
        actionShow.text = Utilities.MovementToString(movementToDo)
        //TODO("VARIABLE TIME")
        actionTimer.Start(5000)
    }

    private fun ProcessMove(movement: Movements)
    {
        //Si todavia no iniciamos, o perdimos, no procesamos nada
        if(!gameStart || lose)
        {
            return
        }

        if (movement == movementToDo)
        {
            //TODO("WIN AND RE PLAY, grown miliseconds")
            actionShow.text = "¡+1 Punto!"
            actionTimer.Stop()
            winSound.start()
            giveTimer.Start(1000)
        }
    }

    override fun onTimerFinished(timer: Timer)
    {
        //Temporizador para dar acciones al jugador
        if(timer.id == GIVE_TIMER)
        {
            gameStart = true
            GiveAction()
        }

        //Temporizador cuando se le acabe el tiempo de hacer la accion (escencialmente perder)
        if(timer.id == ACTION_TIMER)
        {
            //TODO("LOSE, and lose string.xml")
            actionShow.text = "Perdiste"
            lose = true;
            loseSound.start()
        }

        super.onTimerFinished(timer)
    }

    override fun onTimerTick(timer: Timer) {
        if(timer.id == ACTION_TIMER)
        {
            //actualizamos el texto
            actionCountdownText.text = (timer.GetTimeToFinish()/1000).toString()
        }

        if(timer.id == GIVE_TIMER)
        {
            //actualizamos el texto
            actionCountdownText.text = "NUEVA ACCION EN " + (timer.GetTimeToFinish()/1000).toString()
        }
        super.onTimerTick(timer)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(event!= null)
        {
            return gestureDetector.onTouchEvent(event)
        }

        return super.onTouchEvent(event)
    }

    override fun onDown(p0: MotionEvent): Boolean {
        //TODO("Not yet implemented")
        return true
    }

    override fun onShowPress(p0: MotionEvent) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean
    {
        ProcessMove(Movements.Tap)
        return true
    }

    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        //TODO("Not yet implemented")
        return true
    }

    override fun onLongPress(p0: MotionEvent) {
        //TODO("Not yet implemented")
    }

    override fun onFling(start: MotionEvent, finish: MotionEvent, p2: Float, p3: Float): Boolean {

        //obtenemos los deltas para calcular la direccion
        val deltaX = finish.x - start.x
        val deltaY = finish.y - start.y

        if (Math.abs(deltaX) > Math.abs(deltaY))
        {
            // El gesto es horizontal
            if (deltaX > 0)
            {
                ProcessMove(Movements.FlingRIGHT)
            }
            else
            {
                ProcessMove(Movements.FlingLEFT)
            }
        }
        else
        {
            // El gesto es vertical
            if (deltaY > 0)
            {
                ProcessMove(Movements.FlingDOWN)
            }
            else
            {
                ProcessMove(Movements.FlingUP)
            }
        }

        return true
    }

    override fun onSingleTapConfirmed(p0: MotionEvent): Boolean {
        //TODO("Not yet implemented")
        return true
    }

    override fun onDoubleTap(p0: MotionEvent): Boolean {
        //TODO("Not yet implemented")
        return true
    }

    override fun onDoubleTapEvent(p0: MotionEvent): Boolean {
        //TODO("Not yet implemented")
        return true
    }
}