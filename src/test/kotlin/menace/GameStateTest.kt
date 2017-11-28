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

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import menace.Player.O
import menace.Player.X
import org.junit.Test

class GameStateTest() {
    companion object {
        val E = null;
    }

    @Test fun x_winner() {
        val board =  board(
                X, X, X,
                O, O, X,
                O, O, E
        )
        print(render(board))
        assertThat(winner(board)).isEqualTo(X)
    }

    @Test fun o_winner() {
        val board =  board(
                X, X, O,
                O, O, X,
                O, X, E
        )
        print(render(board))
        assertThat(winner(board)).isEqualTo(O)
    }

    @Test fun no_winner() {
        val board =  board(
                X, X, O,
                O, X, X,
                X, O, O
        )
        print(render(board))
        assertThat(winner(board)).isEqualTo(null)
    }


}
