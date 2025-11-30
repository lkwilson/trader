package lwilson.mixin

import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.passive.MerchantEntity
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(MerchantEntity::class)
abstract class MerchantMixin {
  private val logger = LoggerFactory.getLogger("trader.merchant")

  @Inject(method = ["fillRecipesFromPool"], at = arrayOf(At("TAIL")))
  private fun onSetOffersFromServer(info: CallbackInfo) {
    logger.info("onSetOffersFromServer")
    val merchant = this as MerchantEntity
    // if (merchant is VillagerEntity) {
    //     val world = merchant.world ?: return
    //     world.players.forEach { player ->
    //         player.sendMessage(Text.literal("Villager updated!"), true)
    //     }
    // }

    // val client = MinecraftClient.getInstance()
    // val player = client.player
    // player?.sendMessage(Text.literal("Message"), false)
    logger.info("Villager trades updated!")
    for (x in merchant.offers) {
      logger.info("Offer: ${x.sellItem} ${x.sellItem.enchantments.toString()}")
    }
  }
}
