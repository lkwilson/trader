// src/client/kotlin/lkwilson/ClothConfigScreen.kt
package lkwilson

import me.shedaniel.clothconfig2.api.ConfigBuilder
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

fun createConfigScreen(parent: Screen?): Screen {
  val builder =
          ConfigBuilder.create().setParentScreen(parent).setTitle(Text.literal("Trader Config"))

  val general = builder.getOrCreateCategory(Text.literal("General"))

  general.addEntry(
          me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create()
                  .startBooleanToggle(Text.literal("Enable"), Config.modEnabled)
                  .setTooltip(Text.literal("Enable or disable the mod"))
                  .setDefaultValue(true)
                  .setSaveConsumer {
                    Config.modEnabled = it
                    Config.save()
                  }
                  .build()
  )

  general.addEntry(
          me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create()
                  .startBooleanToggle(
                          Text.literal("Use chat instead of overlay"),
                          Config.messageWithChat
                  )
                  .setTooltip(
                          Text.literal("Send trade details to chat instead of an overlay popup")
                  )
                  .setDefaultValue(true)
                  .setSaveConsumer {
                    Config.messageWithChat = it
                    Config.save()
                  }
                  .build()
  )

  general.addEntry(
          me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create()
                  .startBooleanToggle(
                          Text.literal("(multiplayer) Require line of site"),
                          Config.requireLineOfSight
                  )
                  .setTooltip(
                          Text.literal(
                                  "The player must have line of site to the villager (ignored in single player)"
                          )
                  )
                  .setDefaultValue(true)
                  .setSaveConsumer {
                    Config.requireLineOfSight = it
                    Config.save()
                  }
                  .build()
  )

  general.addEntry(
          me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create()
                  .startBooleanToggle(
                          Text.literal("(multiplayer) Require stationary"),
                          Config.requirePlayerStationary
                  )
                  .setTooltip(
                          Text.literal(
                                  "The player must not be moving since requesting villager trades will cancel movement (ignored in single player)"
                          )
                  )
                  .setDefaultValue(true)
                  .setSaveConsumer {
                    Config.requirePlayerStationary = it
                    Config.save()
                  }
                  .build()
  )

  general.addEntry(
          me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create()
                  .startBooleanToggle(
                          Text.literal("(multiplayer) Require standing"),
                          Config.requirePlayerNotSneaking
                  )
                  .setTooltip(
                          Text.literal(
                                  "The player must not be sneaking since requesting villager trades will cancel sneak (ignored in single player)"
                          )
                  )
                  .setDefaultValue(true)
                  .setSaveConsumer {
                    Config.requirePlayerNotSneaking = it
                    Config.save()
                  }
                  .build()
  )

  return builder.build()
}
