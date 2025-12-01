package lkwilson

import java.io.File

object Config {
  var modEnabled: Boolean = true
  var messageWithChat: Boolean = true
  var requireLineOfSight: Boolean = true
  var requirePlayerStationary: Boolean = true
  var requirePlayerNotSneaking: Boolean = true

  fun load() {
    val configFile = File("config/trader.txt")
    if (!configFile.exists()) {
      save()
      return
    }

    configFile.forEachLine { line ->
      val (key, value) =
              line.split("=", limit = 2).let { it[0] to it.getOrNull(1) } ?: return@forEachLine
      when (key.trim()) {
        "modEnabled" -> modEnabled = value?.toBoolean() ?: modEnabled
        "messageWithChat" -> messageWithChat = value?.toBoolean() ?: messageWithChat
        "requireLineOfSight" -> requireLineOfSight = value?.toBoolean() ?: requireLineOfSight
        "requirePlayerStationary" ->
                requirePlayerStationary = value?.toBoolean() ?: requirePlayerStationary
        "requirePlayerNotSneaking" ->
                requirePlayerNotSneaking = value?.toBoolean() ?: requirePlayerNotSneaking
      }
    }
  }

  fun save() {
    val configDir = File("config")
    configDir.mkdirs()
    File("config/trader.txt")
            .writeText(
                    """
                    modEnabled=$modEnabled
                    messageWithChat=$messageWithChat
                    requireLineOfSight=$requireLineOfSight
                    requirePlayerStationary=$requirePlayerStationary
                    requirePlayerNotSneaking=$requirePlayerNotSneaking
                    """.trimIndent()
            )
  }
}
