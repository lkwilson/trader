package lwilson.mixin

import lwilson.TradeSync
import net.minecraft.client.MinecraftClient
import net.minecraft.screen.MerchantScreenHandler
import net.minecraft.village.TradeOfferList
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(MerchantScreenHandler::class)
abstract class MerchantScreenHandlerMixin {
  private val logger = LoggerFactory.getLogger("trader.merchant_screen")

  @Inject(method = ["setOffers"], at = arrayOf(At("TAIL")))
  private fun onSetOffers(offers: TradeOfferList, info: CallbackInfo) {
    if (!TradeSync.pending) return
    val client = MinecraftClient.getInstance()
    if (client.isInSingleplayer()) return
    val player = client.player ?: return
    TradeSync.handleOffers(client, player, offers)
    // This alone has the issue of the server not realizing it's closed, so we
    // have to send the packet
    // client.networkHandler?.sendPacket(CloseHandledScreenC2SPacket(player.currentScreenHandler.syncId))
    // client.setScreen(null);

    // This has the issue of the server not realizing it's closed
    // player.closeScreen()

    // This seems to close / set null and send the packet
    player.closeHandledScreen()

    logger.info("Setting pending to false")
    TradeSync.pending = false
  }
}
