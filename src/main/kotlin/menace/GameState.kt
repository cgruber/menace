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

import menace.Player.Companion.E

data class Cell(val x: Int, val y: Int)

enum class Player {
  X, O;
  companion object {
    val E = null // empty game state element for easy text representation of game state.
  }
}

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
      Pair(Cell(2, 2), p8))
}

/**
 * Checks the supplied board for a winner.  It assumes a board in play, and assumes it is
 * checking at the earliest possibly winning move, and so does not handle cases where the
 * board is set up incoherently or in an invalid state, with more than one winner, or improper
 * numbers of X's or O's.  If there are more than one winner in the game configuration, this
 * method will return the first found.
 */
fun winner(board: Board) : Player? {
  // Check for column wins
  for (row in 0..2) {
    val players = board.filterKeys({ it.x == row }).values
    if (players.size == 3 && players.toSet().size == 1) return players.first()
  }

  // Check for row wins
  skip@ for (col in 0..2) {
    val players = board.filterKeys({ it.y == col }).values
    if (players.size == 3 && players.toSet().size == 1) return players.first()
  }

  // Check diagonal wins
  val left = arrayListOf(board[Cell(0,0)], board[Cell(1, 1)], board[Cell(2,2)])
  if (left.size == 3 && left.toSet().size == 1) return left.first()

  val right = arrayListOf(board[Cell(0,2)], board[Cell(1, 1)], board[Cell(2,0)])
  if (right.size == 3 && right.toSet().size == 1) return right.first()

  // No wins
  return null
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
      E, E, E,
      E, E, E,
      E, E, E)
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


