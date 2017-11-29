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

/** A representation of the tic-tac-toe board. */
@Serializable
data class Board(val state: Map<Cell, Player?>) {
  val winner: Player? by lazy { winner() }

  private fun cell(x: Byte, y: Byte): String = state[Cell(x, y)]?.toString() ?: "E"

  override fun toString(): String {
    return """
      +-+-+-+
      |${cell(0, 0)}|${cell(1, 0)}|${cell(2, 0)}|
      |${cell(0, 1)}|${cell(1, 1)}|${cell(2, 1)}|
      |${cell(0, 2)}|${cell(1, 2)}|${cell(2, 2)}|
      +-+-+-+
    """
  }

  /**
   * Checks the supplied board for a winner.  It assumes a board in play, and assumes it is
   * checking at the earliest possibly winning move, and so does not handle cases where the
   * board is set up incoherently or in an invalid state, with more than one winner, or improper
   * numbers of X's or O's.  If there are more than one winner in the game configuration, this
   * method will return the first found.
   */
  private fun winner() : Player? {
    // Check for column wins
    for (row in 0..2) {
      val players = state.filterKeys({ it.x == row.toByte() }).values
      if (players.size == 3 && players.toSet().size == 1) return players.first()
    }

    // Check for row wins
    skip@ for (col in 0..2) {
      val players = state.filterKeys({ it.y == col.toByte() }).values
      if (players.size == 3 && players.toSet().size == 1) return players.first()
    }

    // Check diagonal wins
    val left = arrayListOf(state[Cell(0,0)], state[Cell(1, 1)], state[Cell(2,2)])
    if (left.size == 3 && left.toSet().size == 1) return left.first()

    val right = arrayListOf(state[Cell(0,2)], state[Cell(1, 1)], state[Cell(2,0)])
    if (right.size == 3 && right.toSet().size == 1) return right.first()

    // No wins
    return null
  }

  fun render(): Unit = println(this);

  companion object {
    fun forPositions(
        p0: Player?, p1: Player?, p2: Player?,
        p3: Player?, p4: Player?, p5: Player?,
        p6: Player?, p7: Player?, p8: Player?): Board {
      return Board(mapOf(
          Pair(Cell(0, 0), p0),
          Pair(Cell(1, 0), p1),
          Pair(Cell(2, 0), p2),
          Pair(Cell(0, 1), p3),
          Pair(Cell(1, 1), p4),
          Pair(Cell(2, 1), p5),
          Pair(Cell(0, 2), p6),
          Pair(Cell(1, 2), p7),
          Pair(Cell(2, 2), p8)))
    }
    fun newBoard() : Board {
      return Board.forPositions(
          Player.E, Player.E, Player.E,
          Player.E, Player.E, Player.E,
          Player.E, Player.E, Player.E)
    }
  }
}
