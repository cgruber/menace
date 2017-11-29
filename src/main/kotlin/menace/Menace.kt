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
@file:JvmName("Menace")

package menace

import kotlinx.serialization.json.JSON
import menace.Player.O
import menace.Player.X

fun main(args : Array<String>) : Unit {
  print("Preparing MENACE engine initial state...")
  val timestamp = System.currentTimeMillis()
  val menace = MenaceState(
      name = "MENACE",
      humanFirst = initializeMatchboxes(X),
      menaceFirst = initializeMatchboxes(O))
  println("Done: ${System.currentTimeMillis() - timestamp} ms.")
  println(JSON.Companion.stringify(menace))
  val gameState = Game(menace, O)


  //render(gameState);
}
