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
}
