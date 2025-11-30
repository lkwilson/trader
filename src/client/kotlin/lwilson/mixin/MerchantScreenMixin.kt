package lwilson.mixin

import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.passive.MerchantEntity
import net.minecraft.text.Text
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import net.minecraft.client.MinecraftClient
import net.minecraft.screen.MerchantScreenHandler
import net.minecraft.village.TradeOfferList


@Mixin(MerchantScreenHandler::class)
abstract class MerchantScreenHandlerMixin {
    private val logger = LoggerFactory.getLogger("trader.merchant_screen")

    @Inject(method = ["setOffers"], at = arrayOf(At("TAIL")))
    private fun onSetOffers(offers: TradeOfferList, info: CallbackInfo) {
        logger.info("setOffers")
        for (offer in offers) {
            logger.info("Offer: ${offer.sellItem}")
        }
    }
}
