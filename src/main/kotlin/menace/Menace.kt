@file:JvmName("Menace")

package menace

fun main(args : Array<String>) : Unit {
    val gameState = GameState()

    val moves = validNextMoves(cleanBoard(), Player.X)
    for (move in moves) {
        render(move.outcome)
    }

    //render(gameState);
}



fun render(board: Map<Cell, Player?>): Unit {
    println("+-+-+-+");
    for (col in 0..2) {
        System.out.print("|")
        for (row in 0..2) {
            val player = board[Cell(row, col)]
            when {
                player == null -> print(" |")
                else -> print("${player}|")
            }
        }
        println()
        println("+-+-+-+");
    }
}
fun render(state: GameState): Unit {
    println("Current player: ${state.turn}")
    render(state.current)
}