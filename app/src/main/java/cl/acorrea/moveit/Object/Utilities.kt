package cl.acorrea.moveit.Object

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
        }
    }
}