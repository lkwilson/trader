package lwilson.mixin

import net.minecraft.entity.passive.VillagerEntity
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(VillagerEntity::class)
abstract class VillagerMixin {
  private val logger = LoggerFactory.getLogger("trader.villager")

  // @Inject(method = ["setOffers", "prepareOffersFor"], at = arrayOf(At("HEAD")))
  // private fun onTradesUpdated(info: CallbackInfo) {
  //   logger.info("onTradesUpdated")
  //   val villager = this as VillagerEntity
  //   // val client = MinecraftClient.getInstance()
  //   // val player = client.player
  //   // player?.sendMessage(Text.literal("Message"), false)
  //   logger.info("Villager trades updated!")
  //   for (x in villager.offers) {
  //     logger.info("Offer: ${x.sellItem} ${x.sellItem.enchantments.toString()}")
  //   }
  // }

  // @Inject(method = arrayOf("prepareOffersFor"), at = arrayOf(At("HEAD")))
  // private fun onPrepareOffersFor(info: CallbackInfo) {
  //   logger.info("onPrepareOffersFor")
  //   val villager = this as VillagerEntity
  //   logger.info("Villager trades updated!")
  //   for (x in villager.offers) {
  //     logger.info("Offer: ${x.sellItem} ${x.sellItem.enchantments.toString()}")
  //   }
  // }

  // @Inject(method = arrayOf("beginTradeWith"), at = arrayOf(At("HEAD")))
  // private fun onBeginTradeWith(info: CallbackInfo) {
  //   logger.info("onBeginTradeWith")
  //   val villager = this as VillagerEntity
  //   logger.info("Villager trades updated!")
  //   for (x in villager.offers) {
  //     logger.info("Offer: ${x.sellItem} ${x.sellItem.enchantments.toString()}")
  //   }
  // }
}
