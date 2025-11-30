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
    client.setScreen(null)
    logger.info("Setting pending to false")
    TradeSync.pending = false
  }
}
