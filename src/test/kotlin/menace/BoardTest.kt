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
import menace.Player.Companion.E
import menace.Player.O
import menace.Player.X
import org.junit.Test

class BoardTest() {
  @Test fun x_winner_accross() {
    assertThat(Board.forPositions(
        X, X, X,
        O, O, X,
        O, O, E
    ).winner).isEqualTo(X)
    assertThat(Board.forPositions(
        O, O, X,
        X, X, X,
        O, O, E
    ).winner).isEqualTo(X)
    assertThat(Board.forPositions(
        O, O, X,
        O, O, E,
        X, X, X
    ).winner).isEqualTo(X)
  }

  @Test fun x_winner_down() {
    assertThat(Board.forPositions(
        X, O, X,
        O, O, X,
        O, E, X
    ).winner).isEqualTo(X)
    assertThat(Board.forPositions(
        X, X, O,
        O, X, O,
        O, X, E
    ).winner).isEqualTo(X)
    assertThat(Board.forPositions(
        X, X, O,
        X, O, O,
        X, O, E
    ).winner).isEqualTo(X)
  }

  @Test fun x_winner_diagonal() {
    assertThat(Board.forPositions(
        O, O, X,
        X, X, O,
        X, O, E
    ).winner).isEqualTo(X)
    assertThat(Board.forPositions(
        X, X, O,
        O, X, O,
        E, O, X
    ).winner).isEqualTo(X)
  }

  @Test fun o_winner_across() {
    assertThat(Board.forPositions(
        O, O, O,
        X, X, E,
        O, X, X
    ).winner).isEqualTo(O)
    assertThat(Board.forPositions(
        X, X, E,
        O, O, O,
        O, X, X
    ).winner).isEqualTo(O)
    assertThat(Board.forPositions(
        X, X, E,
        O, X, X,
        O, O, O
    ).winner).isEqualTo(O)
  }

  @Test fun o_winner_down() {
    assertThat(Board.forPositions(
        O, X, X,
        O, O, X,
        O, X, E
    ).winner).isEqualTo(O)
    assertThat(Board.forPositions(
        X, O, X,
        O, O, X,
        X, O, E
    ).winner).isEqualTo(O)
    assertThat(Board.forPositions(
        X, X, O,
        X, O, O,
        E, X, O
    ).winner).isEqualTo(O)
  }

  @Test fun o_winner_diagonal() {
    assertThat(Board.forPositions(
        X, X, O,
        O, O, X,
        O, X, E
    ).winner).isEqualTo(O)
    assertThat(Board.forPositions(
        O, X, X,
        O, O, X,
        E, X, O
    ).winner).isEqualTo(O)
  }

  @Test fun no_winner_start() {
    val board = Board.forPositions(
        E, E, E,
        E, E, E,
        E, E, E
    )
    assertThat(board.winner).isEqualTo(null)
  }

  @Test fun no_winner_yet() {
    val board = Board.forPositions(
        X, X, E,
        O, X, X,
        E, O, O
    )
    assertThat(board.winner).isEqualTo(null)
  }

  @Test fun no_winner_finished() {
    val board = Board.forPositions(
        X, X, O,
        O, X, X,
        X, O, O
    )
    assertThat(board.winner).isEqualTo(null)
  }
}
