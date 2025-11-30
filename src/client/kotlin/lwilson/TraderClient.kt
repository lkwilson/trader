package lwilson

import net.fabricmc.api.ClientModInitializer

// import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
// import net.minecraft.client.MinecraftClient
// import net.minecraft.client.gui.screen.ingame.MerchantScreen
// import net.minecraft.screen.MerchantScreenHandler

// var tickCounter = 0

// ClientTickEvents.END_CLIENT_TICK.register { client ->
//     tickCounter++
    
//     if (tickCounter == 20) {  // Trigger after 1 second (20 ticks)
//         val player = client.player ?: return@register
//         val world = client.world ?: return@register
        
//         // Find nearest villager
//         val villager = world.getPlayers { true }
//             .flatMap { world.getEntities() }
//             .filterIsInstance<VillagerEntity>()
//             .minByOrNull { it.squaredDistanceTo(player) }
//             ?: return@register
        
//         if (player.squaredDistanceTo(villager) < 100.0) {
//             // Open merchant screen
//             val handler = MerchantScreenHandler(0, player.inventory, villager)
//             client.setScreen(MerchantScreen(handler, player.inventory, villager, Text.literal("Villager")))
//         }
//     }
    
//     if (tickCounter == 25) {  // Close after 0.25 seconds
//         client.setScreen(null)
//         tickCounter = 0
//     }
// }

object TraderClient : ClientModInitializer {
	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}