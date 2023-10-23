package cl.acorrea.moveit.entities

import android.os.CountDownTimer
import cl.acorrea.moveit.Interface.TimerCallback

class Timer(private val callback: TimerCallback, val id: String = "NOID")
{
    private var timer: CountDownTimer? = null
    private var milliseconds: Long = 0
    private var elapsedMilliseconds: Long = 0
    private var millisToFinish: Long = 0
    fun Start(milliseconds: Long)
    {
        this.milliseconds = milliseconds
        createTimer()
        timer?.start()
    }

    fun IsRunning() : Boolean
    {
        return timer != null
    }

    fun Stop()
    {
        timer?.cancel()
        timer = null
    }

    fun GetElapsedTime() : Long
    {
        return elapsedMilliseconds
    }

    fun GetTimeToFinish() : Long
    {
        return millisToFinish
    }

    private fun createTimer()
    {
        elapsedMilliseconds = 0
        millisToFinish = milliseconds
        timer = object : CountDownTimer(milliseconds,1000)
        {
            override fun onTick(millisUntilFinish: Long) {
                callback.onTimerTick(this@Timer)
                elapsedMilliseconds = milliseconds - millisUntilFinish
                millisToFinish = millisUntilFinish
            }

            override fun onFinish() {
                callback.onTimerFinished(this@Timer)
                timer = null
            }

        }
    }
}