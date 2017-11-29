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

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.ArrayDeque

@Serializable
data class Cell(val x: Byte, val y: Byte) {
  constructor(x: Int, y: Int) : this(x.toByte(), y.toByte())
  override fun toString(): String = "($x,$y)"
}

enum class Player {
  X, O;
  companion object {
    val E = null // newBoard game state element for easy text representation of game state.
  }
}

class Game (val current: MenaceState, val human: Player) {
  var turn: Player = Player.X
  var board: Board = Board.newBoard()
}

@Serializable
data class Move (val initial: Board, val next: Cell, val player: Player) {
  init {
    check(initial.state[next] == null) {
      "Next move ($next.x, $next.y) is already taken."
    }
  }

  /** The resulting board state after this move, lazily computed */
  @Transient
  val outcome : Board by lazy { Board(initial.state.plus(Pair(next, player))) }

  /** Is this move a winning move for the given player */
  @Transient
  val winning : Boolean by lazy { outcome.winner == player }
}

@Serializable
class MenaceState(
    val name: String,
    val humanFirst: Set<Matchbox>,
    val menaceFirst: Set<Matchbox>)

@Serializable
data class Matchbox(val board : Board) {
  val moves : MutableMap<Move, Int> = mutableMapOf()

  override fun toString(): String {
    val next = moves.mapKeys { it.key.next }
    return "Matchbox(board=${board}, next=${next}"
  }
}


fun initializeMatchboxes(human: Player, initial: Board = Board.newBoard()): Set<Matchbox> {
  // Human and Menace play opposite
  val menace = when (human) {
    Player.X -> Player.O
    Player.O -> Player.X
  }

  val matchboxes: MutableSet<Matchbox> = mutableSetOf()
  val boards: ArrayDeque<Board> = ArrayDeque()
  boards.add(initial)

  while (!boards.isEmpty()) {
    val board = boards.poll()

    val nextBoards = when {
      menace == Player.X && board == Board.newBoard() -> setOf(board) // skip if menace goes first
      else -> {
        // Human turn
        availableMoves(board, human)
            .map { it.outcome }
            .filter { it.winner == null } // ignore winning boards, since play can't continue
      }
    }

    // Set up machine states for subsequent moves
    nextBoards
        .flatMap { availableMoves(it, menace) }
        .groupBy { it.initial }
        .forEach {
          if (!it.value.isEmpty()) {
            val matchbox = Matchbox(it.key)
            if (!matchboxes.contains(matchbox)) {
              matchboxes.add(matchbox)
              it.value.forEach {
                matchbox.moves.put(it, 4)
                if (it.outcome.winner == null) boards.add(it.outcome)
              }
            }
          }
        }
  }
  return matchboxes
}



fun availableMoves(initial: Board, turn: Player) : Set<Move> {
    val moves = mutableSetOf<Move>()

    for (x in 0..2) {
      for (y in 0..2) {
        if (initial.state[Cell(x, y)] == null) {
            moves.add(Move(initial, Cell(x, y), turn))
        }
      }
    }
    return moves
}


