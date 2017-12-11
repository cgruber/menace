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

import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.JSON
import menace.Board.Companion.forPositions
import menace.Player.Companion.E
import menace.Player.O
import menace.Player.X
import org.junit.Test

class GameStateTest() {

  @Test fun setupMatchboxesHumanStart() {
    val matchboxes = initializeMatchboxes(human = X)
    assertThat(matchboxes.filter { it.board.winner != null }).isEmpty()
    assertThat(matchboxes).hasSize(2115)
  }

  @Test fun setupMatchboxesMenaceStart() {
    val matchboxes = initializeMatchboxes(human = O)
    assertThat(matchboxes.filter { it.board.winner != null }).isEmpty()
    assertThat(matchboxes).hasSize(2427)
  }


  val json = JSON(unquoted = true, indented = true, indent = "  ")
  val jsonText =
"""{
  name: "MENACE 2",
  humanFirst: [
    {
      board: {
        state: [
          O,
          X,
          X,
          null,
          X,
          O,
          null,
          O,
          null
        ]
      },
      moves: [
        {
          move: {
            next: 3,
            player: X
          }
        },
        {
          move: {
            next: 3,
            player: X
          }
        },
        {
          move: {
            next: 3,
            player: X
          }
        },
        {
          move: {
            next: 3,
            player: X
          }
        }
      ]
    }
  ],
  menaceFirst: [
  ]
}"""
  val board = forPositions(
      O, X, X,
      E, X, O,
      E, O, E)
  val menace = MenaceState(
      name = "MENACE 2",
      humanFirst = setOf(Matchbox(board).also {
        for (i in 0..3) {
          it.moves.add(Bead(Move(3, X)))
        }
      }),
      menaceFirst = setOf())

  @Test fun writeOut() {
    val outputString = json.stringify(menace)
    assertThat(outputString).isEqualTo(jsonText);
  }

  @Test fun readIn() {
    val parsed: MenaceState = json.parse(jsonText)
    assertThat(parsed).isEqualTo(menace)
  }
}
