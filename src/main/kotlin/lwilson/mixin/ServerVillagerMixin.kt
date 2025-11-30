package lwilson.mixin

import net.minecraft.entity.passive.VillagerEntity
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(VillagerEntity::class)
abstract class ServerVillagerMixin {
  private val logger = LoggerFactory.getLogger("trader.svr.villager")

  private fun handleUpdate() {
    val villager = this as VillagerEntity
    logger.info("Villager trades updated!")
    for (x in villager.offers) {
      logger.info("Offer: ${x.sellItem} ${x.sellItem.enchantments.toString()}")
    }
  }

  @Inject(method = ["setOffers"], at = arrayOf(At("HEAD")))
  private fun onSetOffers(info: CallbackInfo) {
    logger.info("setOffers")
    handleUpdate()
  }

  @Inject(method = arrayOf("prepareOffersFor"), at = arrayOf(At("HEAD")))
  private fun onPrepareOffersFor(info: CallbackInfo) {
    logger.info("prepareOffersFor")
    handleUpdate()
  }

  @Inject(method = arrayOf("beginTradeWith"), at = arrayOf(At("HEAD")))
  private fun onBeginTradeWith(info: CallbackInfo) {
    logger.info("beginTradeWith")
    handleUpdate()
  }
}
