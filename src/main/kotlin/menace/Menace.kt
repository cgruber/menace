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

import com.beust.jcommander.Parameter
import kotlinx.serialization.json.JSON
import menace.Player.O
import menace.Player.X
import com.beust.jcommander.IStringConverter
import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import kotlin.system.exitProcess


fun main(vararg argv : String) : Unit {
  val args = Args()
  var processor = JCommander.newBuilder().addObject(args).build()
  var valid = true
  try {
    processor.parse(*argv);
    valid = args.validate()
  } catch(e: ParameterException) {
    println(e.message)
    valid = false
  }
  if (!valid || args.help) {
    processor.usage()
    exitProcess(when (args.help) { true -> 0 false -> 1 })
  }
  Main(FileSystems.getDefault()).main(args.name!!, args.userPlaysAs)
}

class Main(val filesystem: FileSystem) {
  companion object {
    @JvmStatic val json = JSON(unquoted = true, indented = true, indent = "  ")
  }

  fun main(name: String, humanPlayer: Player) : Unit {
    val path = filesystem.getPath("${name}.menace")

    // load or initialize
    val menace: MenaceState = when {
      Files.isRegularFile(path) -> {
        print("Loading MENACE engine state for \"${name}\"...")
        val loaded: MenaceState = json.parse(path.readText())
        println("loaded.")
        loaded // return
      }
      else -> {
        // Initialize and save menace engine state
        println("Preparing MENACE engine initial state for \"${name}\"...")
        val initial = MenaceState(
            name = name, // validated early.
            humanFirst = initializeMatchboxes(X),
            menaceFirst = initializeMatchboxes(O))
        path.writeText(json.stringify(initial))
        initial // return
      }
    }
    val modified = Files.getLastModifiedTime(path)

    // play-game

    val gameState = Game(menace, O)
    //render(gameState);


    // Save game state
    println("Writing ${name} game state:\n ${menace}")
    path.writeText(json.stringify(menace))
  }
}

class Args {
  @Parameter(
      names = arrayOf("-n", "--name"),
      description = "The MENACE player to be played against.")
  var name: String? = null

  @Parameter(
      names = arrayOf("-p", "--playAs"),
      description = "The human player's position (X or O)",
      converter = Playerconverter::class)
  var userPlaysAs: Player = X

  @Parameter(names = arrayOf("-h", "--help"), help = true)
  var help: Boolean = false

  /** Validates the arguments, returning false if the parameters aren't valid */
  fun validate(): Boolean {
    var valid = true;
    if (name == null) {
      println("You must specify a name for the menace machine you want to play.")
      valid = false
    }
    return valid
  }
}

class Playerconverter : IStringConverter<Player> {
  override fun convert(value: String): Player = Player.valueOf(value)
}