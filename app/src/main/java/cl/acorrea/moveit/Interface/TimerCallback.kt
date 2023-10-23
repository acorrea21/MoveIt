package cl.acorrea.moveit.Interface

import cl.acorrea.moveit.entities.Timer

interface TimerCallback {
    fun onTimerFinished(timer: Timer)
    {

    }
    fun onTimerTick(timer: Timer)
    {

    }
}