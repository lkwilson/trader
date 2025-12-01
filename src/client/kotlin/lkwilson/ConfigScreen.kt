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
                  .startBooleanToggle(Text.literal("Enable the Trader mod"), Config.modEnabled)
                  .setSaveConsumer {
                    Config.modEnabled = it
                    Config.save()
                  }
                  .build()
  )

  general.addEntry(
          me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create()
                  .startBooleanToggle(
                          Text.literal("Send trade details to chat instead of an overlay popup"),
                          Config.messageWithChat
                  )
                  .setSaveConsumer {
                    Config.messageWithChat = it
                    Config.save()
                  }
                  .build()
  )

  general.addEntry(
          me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create()
                  .startBooleanToggle(
                          Text.literal("Require line of site (server only)"),
                          Config.requireLineOfSight
                  )
                  .setSaveConsumer {
                    Config.requireLineOfSight = it
                    Config.save()
                  }
                  .build()
  )

  general.addEntry(
          me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create()
                  .startBooleanToggle(
                          Text.literal(
                                  "Require player to be stationary as requesting villager trades will cancel movement"
                          ),
                          Config.requirePlayerStationary
                  )
                  .setSaveConsumer {
                    Config.requirePlayerStationary = it
                    Config.save()
                  }
                  .build()
  )

  general.addEntry(
          me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create()
                  .startBooleanToggle(
                          Text.literal(
                                  "Require player to not be sneaking as requesting villager trades will cancel sneak"
                          ),
                          Config.requirePlayerNotSneaking
                  )
                  .setSaveConsumer {
                    Config.requirePlayerNotSneaking = it
                    Config.save()
                  }
                  .build()
  )

  return builder.build()
}
