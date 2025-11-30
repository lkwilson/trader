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

    @Inject(method = arrayOf("setOffers"), at = arrayOf(At("HEAD")))
    private fun onTradesUpdated(info: CallbackInfo) {
        val villager = this as VillagerEntity
        // val client = MinecraftClient.getInstance()
        // val player = client.player
        // player?.sendMessage(Text.literal("Message"), false)
        logger.info("Villager trades updated!")
        logger.info("Profession: ${villager.villagerData.profession}")
        logger.info("Offers: ${villager.offers}")
    }

    @Inject(method = arrayOf("prepareOffersFor"), at = arrayOf(At("HEAD")))
    private fun onPrepareOffersFor(info: CallbackInfo) {
        val villager = this as VillagerEntity
        logger.info("Villager trades updated!")
        logger.info("Profession: ${villager.villagerData.profession}")
        logger.info("Offers: ${villager.offers}")
    }

    @Inject(method = arrayOf("beginTradeWith"), at = arrayOf(At("HEAD")))
    private fun onBeginTradeWith(info: CallbackInfo) {
        val villager = this as VillagerEntity
        logger.info("Villager trades updated!")
        logger.info("Profession: ${villager.villagerData.profession}")
        logger.info("Offers: ${villager.offers}")
    }

}