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

/** A representation of the tic-tac-toe board. */
@Serializable
data class Board private constructor (private val state: List<Player?>) {

  init {
    check(state.size == 9) { "A tic-tac-toe board must have 9 places" }
  }

  operator fun get(index: Int): Player? = state[index]

  fun move(position: Int, player: Player?): Board {
    check(position in 0..8) { "position (${position}) must be between 0 and 8"}
    check(state[position] == null) {
      "position (${position}) was already played by ${state[position]}"
    }
    return Board(state.toMutableList().apply { this[position] = player })
  }

  fun move(move: Move): Board = move(move.next, move.player)

  @Transient
  val winner: Player? by lazy { winner() }

  override fun toString(): String {
    return """
      +-+-+-+
      |${state[0] ?: "E"}|${state[1] ?: "E"}|${state[2] ?: "E"}|
      |${state[3] ?: "E"}|${state[4] ?: "E"}|${state[5] ?: "E"}|
      |${state[6] ?: "E"}|${state[7] ?: "E"}|${state[8] ?: "E"}|
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
    for (col in 0..2) {
      val players = state.slice(setOf(col, col+3, col+6))
      if (players.size == 3 && players.toSet().size == 1) return players.first()
    }

    // Check for row wins
    for (row in 0..2) {
      val start = row * 3
      val players = state.slice(IntRange(start, start+2))
      if (players.size == 3 && players.toSet().size == 1) return players.first()
    }

    // Check diagonal wins
    val left = state.slice(arrayListOf(0, 4, 8))
    if (left.size == 3 && left.toSet().size == 1) return left.first()

    val right = arrayListOf(state[2], state[4], state[6])
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
      return Board(arrayListOf(
          p0, p1, p2,
          p3, p4, p5,
          p6, p7, p8))
    }

    fun newBoard() : Board {
      return Board.forPositions(
          Player.E, Player.E, Player.E,
          Player.E, Player.E, Player.E,
          Player.E, Player.E, Player.E)
    }
  }
}
