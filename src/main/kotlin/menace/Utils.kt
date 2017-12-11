package menace

import com.google.common.collect.HashMultiset
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path


fun Path.writeText(text: String, charset: Charset = Charsets.UTF_8): Unit =
    writeBytes(text.toByteArray(charset))

fun Path.writeBytes(array: ByteArray): Unit =
    Files.newOutputStream(this).use {
      it.write(array)
      it.flush()
    }

fun Path.readText(charset: Charset = Charsets.UTF_8): String = readBytes().toString(charset)

fun Path.readBytes(): ByteArray = Files.newInputStream(this).use { input ->
  var offset = 0
  var remaining = Files.size(this).let {
    if (it > Int.MAX_VALUE)
      throw OutOfMemoryError("File $this is too big ($it bytes) to fit in memory.")
    else it
  }.toInt()
  val result = ByteArray(remaining)
  while (remaining > 0) {
    val read = input.read(result, offset, remaining)
    if (read < 0) break
    remaining -= read
    offset += read
  }
  if (remaining == 0) result else result.copyOf(offset)
}