package lwilson.mixin

import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.passive.MerchantEntity
import net.minecraft.text.Text
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(MerchantEntity::class)
abstract class ServerMerchantMixin {
  private val logger = LoggerFactory.getLogger("trader.svr.merchant")

  private fun handleUpdate() {
    logger.info("Villager trades updated!")
    val merchant = this as MerchantEntity
    if (!(merchant is VillagerEntity)) return
    logger.info("Villager updated:")
    for (x in merchant.offers) {
      logger.info("\n  - ${x.sellItem}")
    }
  }

  @Inject(method = ["setOffersFromServer"], at = arrayOf(At("TAIL")))
  private fun onSetOffersFromServer(info: CallbackInfo) {
    logger.info("setOffersFromServer")
    handleUpdate()
  }

  @Inject(method = ["fillRecipesFromPool"], at = arrayOf(At("TAIL")))
  private fun onFillRecipesFromPool(info: CallbackInfo) {
    logger.info("fillRecipesFromPool")
    handleUpdate()
  }
}
