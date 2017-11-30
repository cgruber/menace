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
import java.util.ArrayDeque

typealias Cell = Int

@Serializable
data class Bead(val move: Move)

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
data class Move (val next: Cell, val player: Player)

@Serializable
data class MenaceState(
    val name: String,
    val humanFirst: Set<Matchbox>,
    val menaceFirst: Set<Matchbox>)

@Serializable
data class Matchbox(val board : Board) {
  // property to exclude from data class calculation of equals/hashcode
  val beads: MutableList<Bead> = mutableListOf()
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
            .map { it.first.move(it.second) }
            .filter { it.winner == null } // ignore winning boards, since play can't continue
      }
    }

    // Set up machine states for subsequent moves
    nextBoards
        .flatMap { availableMoves(it, menace) }
        .groupBy { it.first }
        .forEach {
          if (!it.value.isEmpty()) {
            val matchbox = Matchbox(it.key)
            if (!matchboxes.contains(matchbox)) {
              matchboxes.add(matchbox)
              it.value.forEach {
                for (count in 0..3) {
                  matchbox.beads.add(Bead(it.second))
                }
                val outcome = matchbox.board.move(it.second)
                if (outcome.winner == null) boards.add(outcome)
              }
            }
          }
        }
  }
  return matchboxes
}



fun availableMoves(initial: Board, turn: Player) : Set<Pair<Board,Move>> {
    val moves: MutableSet<Pair<Board, Move>> = mutableSetOf()

    for (position in 0..8) {
      if (initial[position] == null) {
          moves.add(initial to Move(position, turn))
      }
    }
    return moves
}


