package lwilson

import net.fabricmc.api.ClientModInitializer

object TraderClient : ClientModInitializer {
  override fun onInitializeClient() {
    // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    Config.load()
  }
}
