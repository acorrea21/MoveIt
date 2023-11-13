package cl.acorrea.moveit.Object

import android.content.Context
import androidx.preference.PreferenceManager
import cl.acorrea.moveit.Enum.Movements
import kotlin.random.Random

object Utilities
{
    fun GetRandomMovement() : Movements
    {
        val values = Movements.values()
        val random = Random(System.currentTimeMillis())
        val index = random.nextInt(values.size)
        return values[index]
    }

    fun MovementToString(move: Movements) : String
    {
        //TODO("RETURN STRING OF ENUM")
        return when(move)
        {
            Movements.Tap -> "¡Toca!"
            Movements.FlingUP -> "¡Hacia arriba!"
            Movements.FlingDOWN -> "¡Hacia abajo!"
            Movements.FlingLEFT -> "¡Hacia la izquierda!"
            Movements.FlingRIGHT -> "¡Hacia la derecha!"
            Movements.Shake -> "¡Agita el celular!"
        }
    }

    fun GetInitialReactionTime(context: Context): Int
    {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)

        val value = pref.getString("initalReactionTime", "5000")?.toIntOrNull()
        if(value == null)
        {
            return 5000
        }
        return value
    }

    fun GetPlayIntro(context: Context): Boolean
    {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getBoolean("intro", true)
    }

    fun GetPlayername(context: Context): String
    {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getString("playername", "Jugador")?:"Jugador"
    }

    fun GetDiff(context: Context) : Int
    {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val value = pref.getString("actionToDifficult", "2")?.toIntOrNull()

        if(value == null)
        {
            return 2
        }

        return value
    }

    fun GetHighscore(context: Context) : Int
    {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val value = pref.getString("highscore", "0")?.toIntOrNull()

        if(value == null)
        {
            return 0
        }

        return value
    }

    fun SetHighscore(context: Context, newHighscore: Int)
    {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("highscore", newHighscore.toString())
        editor.apply()
    }
}