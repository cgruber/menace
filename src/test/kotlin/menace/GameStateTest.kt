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

class GameStateTest() {


  @Test fun x_winner() {
    val board =  board(
        X, X, X,
        O, O, X,
        O, O, E
    )
    assertThat(winner(board)).isEqualTo(X)
  }

  @Test fun o_winner() {
    val board =  board(
        X, X, O,
        O, O, X,
        O, X, E
    )
    assertThat(winner(board)).isEqualTo(O)
  }

  @Test fun no_winner_start() {
    val board =  board(
        E, E, E,
        E, E, E,
        E, E, E
    )
    assertThat(winner(board)).isEqualTo(null)
  }

  @Test fun no_winner_yet() {
    val board =  board(
        X, X, E,
        O, X, X,
        E, O, O
    )
    assertThat(winner(board)).isEqualTo(null)
  }

  @Test fun no_winner_finished() {
    val board =  board(
        X, X, O,
        O, X, X,
        X, O, O
    )
    assertThat(winner(board)).isEqualTo(null)
  }
}
