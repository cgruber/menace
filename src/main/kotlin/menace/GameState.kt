/*
 * Copyright (C) 2017 The MENACE Kotlin Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package menace

data class Cell(val x: Int, val y: Int)

enum class Player { X, O }

typealias Board = Map<Cell, Player?>

class GameState (var current: Board = cleanBoard(), var turn: Player = Player.X)

data class Move (val initial: Board, val next: Cell, val player: Player) {
  val outcome : Board by lazy { initial.plus(Pair(next, player)) }
  val winning : Boolean by lazy { winner(outcome) == player }
}

fun board(
        p0: Player?, p1: Player?, p2: Player?,
        p3: Player?, p4: Player?, p5: Player?,
        p6: Player?, p7: Player?, p8: Player?): Board {
    return mapOf(
            Pair(Cell(0, 0), p0),
            Pair(Cell(1, 0), p1),
            Pair(Cell(2, 0), p2),
            Pair(Cell(0, 1), p3),
            Pair(Cell(1, 1), p4),
            Pair(Cell(2, 1), p5),
            Pair(Cell(0, 2), p6),
            Pair(Cell(1, 2), p7),
            Pair(Cell(2, 2), p8)
    )
}

fun winner(board: Board) : Player? {
    return when (board[Cell(0,0)]) {
        Player.X -> {
            
        }
        Player.O -> null
        null -> {
            when (board[Cell(1, 1)]) {
                Player.X -> null
                Player.O -> null
                null -> {
                    when(board[Cell(2, 2)]) {
                        Player.X -> null
                        Player.O -> null
                        null -> {
                            return null
                        }
                    }
                }
            }
        }
    }
}

class MenaceState(val matchboxes: MutableMap<Move, Int>)


class Matchbox(
    val board : Board,
    val moves : Map<Move, Int>
)

fun initializeMatchboxes(): MutableMap<Move, Int> {
    val matchboxes = mutableMapOf<Move, Int>()
    val nextMoves = validNextMoves(cleanBoard(), Player.X)
    for (move in nextMoves) {
        matchboxes.put(move, 4)
    }
    return matchboxes
}

fun cleanBoard() : Board {
    return board(
        null, null, null,
        null, null, null,
        null, null, null)
}

fun validNextMoves(initial: Board, turn: Player) : Set<Move> {
    val moves = mutableSetOf<Move>()

    for (x in 0..2) {
        for (y in 0..2) {
            if (initial[Cell(x, y)] == null) {
                moves.add(Move(initial, Cell(x, y), turn))
            }
        }
    }
    return moves
}


