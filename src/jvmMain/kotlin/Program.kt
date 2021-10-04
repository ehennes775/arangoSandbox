import Program.Companion.EXIT_FAILURE
import Program.Companion.EXIT_SUCCESS
import kotlin.system.exitProcess

class Program(args: Array<String>) {

    fun execute() {
        println("Hello world!")
    }

    companion object {
        const val EXIT_FAILURE: Int = 1
        const val EXIT_SUCCESS: Int = 0
    }
}

fun main(args: Array<String>) {
    exitProcess(
        try {
            Program(args).execute()
            EXIT_SUCCESS
        } catch (e: Exception) {
            println(e.message)
            EXIT_FAILURE
        }
    )
}
