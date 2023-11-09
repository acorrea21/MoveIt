package cl.acorrea.moveit

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import cl.acorrea.moveit.Interface.TimerCallback
import cl.acorrea.moveit.Enum.Movements
import cl.acorrea.moveit.Object.Utilities
import cl.acorrea.moveit.entities.Timer
import java.math.RoundingMode
import java.text.DecimalFormat

class GameActivity : AppCompatActivity(), GestureDetector.OnGestureListener
    , GestureDetector.OnDoubleTapListener, TimerCallback, SensorEventListener {

    //TODO(TO STRING.XML)
    private val GIVE_TIMER = "give"
    private val ACTION_TIMER = "action"
    private val HIGHSCORE_ID = "highscore"
    private val STARTACTIONTIMER = "action"


    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var sensorManager: SensorManager
    private lateinit var acelerometer: Sensor
    private lateinit var sharedPreferences: SharedPreferences
    private var timersFormat: DecimalFormat = DecimalFormat("#.#")

    private lateinit var actionShow: TextView
    private lateinit var actionCountdownText: TextView
    private lateinit var scoreText: TextView
    private lateinit var highscoreText: TextView
    private lateinit var backMusic: MediaPlayer
    private lateinit var winSound: MediaPlayer
    private lateinit var loseSound: MediaPlayer

    private var gameStart: Boolean = false
    private var lose: Boolean = false
    private lateinit var movementToDo: Movements
    private lateinit var giveTimer: Timer
    private lateinit var actionTimer: Timer
    private var milisecondsGame: Int = 0
    private var highscore: Int = 0
    private var score: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        actionShow = findViewById<TextView>(R.id.GameActionText)
        actionCountdownText = findViewById<TextView>(R.id.GameCountdownTime)
        scoreText = findViewById<TextView>(R.id.gameActualScoreView)
        highscoreText = findViewById<TextView>(R.id.gameHighscoreView)
        backMusic = MediaPlayer.create(this, R.raw.background)
        winSound = MediaPlayer.create(this, R.raw.win)
        loseSound = MediaPlayer.create(this, R.raw.fail)
        timersFormat.roundingMode = RoundingMode.DOWN

        giveTimer = Timer(this,100, GIVE_TIMER)
        actionTimer = Timer(this,100, ACTION_TIMER)

        gestureDetector = GestureDetectorCompat(this, this)
        gestureDetector.setOnDoubleTapListener(this)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_GAME)

        sharedPreferences = getSharedPreferences(getString(R.string.SharedPref), MODE_PRIVATE)

        milisecondsGame = sharedPreferences.getInt(ACTION_TIMER,5000)
        highscore = sharedPreferences.getInt(HIGHSCORE_ID, 0)

        SetScores()

        giveTimer.Start(5000)
    }

    override fun onPause() {
        super.onPause()

        backMusic?.pause()
        winSound?.stop()
        loseSound?.stop()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        //TODO("ASK WHEN RESUME")
        backMusic?.start()

        sensorManager.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_GAME)

        //winSound?.start()
        //loseSound?.start()
    }

    private fun GiveAction() {
        movementToDo = Utilities.GetRandomMovement()
        actionShow.text = Utilities.MovementToString(movementToDo)
        //TODO("VARIABLE TIME")
        milisecondsGame = (milisecondsGame * 0.9f).toInt()
        actionTimer.Start(5000)
    }

    private fun ProcessMove(movement: Movements) {
        //Si todavia no iniciamos, o perdimos, no procesamos nada
        if (!gameStart || lose || giveTimer.IsRunning()) {
            return
        }

        if (movement == movementToDo) {
            //TODO("WIN AND RE PLAY, grown miliseconds")
            score++;
            SetScores()
            actionShow.text = "¡+1 Punto!"
            actionTimer.Stop()
            winSound.start()
            giveTimer.Start(1000)
        }
    }

    fun SetScores()
    {
        if(score > highscore)
        {
            highscore = score
        }

        //TODO(XML)
        highscoreText.text = "Puntaje maximo: " + highscore.toString()
        scoreText.text = "Puntaje: " + score.toString()

    }

    fun SaveScores()
    {
        val Editor = sharedPreferences.edit()
        Editor.putInt(HIGHSCORE_ID,highscore)
        Editor.apply()
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
            SaveScores()
            actionShow.text = "Perdiste"

            if(score == highscore)
            {
                actionCountdownText.text = "¡Tuviste un puntaje alto de " + highscore.toString() + "!"
            }
            else
            {
                actionCountdownText.text = "¡Tuviste un puntaje de " + highscore.toString() + "!"
            }

            lose = true;
            loseSound.start()
        }

        super.onTimerFinished(timer)
    }

    override fun onTimerTick(timer: Timer) {
        if(timer.id == ACTION_TIMER)
        {
            //actualizamos el texto
            var t : Float = timer.GetTimeToFinish().toFloat()/1000f

            actionCountdownText.text = timersFormat.format(t)
        }

        if(timer.id == GIVE_TIMER)
        {
            //actualizamos el texto
            var t : Float = timer.GetTimeToFinish().toFloat()/1000f
            actionCountdownText.text = "NUEVA ACCION EN " + timersFormat.format(t)
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

    override fun onSensorChanged(event: SensorEvent)
    {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val acceleration = Math.sqrt(x * x + y * y + z * z.toDouble()).toFloat()

            val threshold = 50.0f

            if (acceleration > threshold)
            {
                ProcessMove(Movements.Shake)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}