package lkwilson

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.text.Text
import net.minecraft.village.TradeOfferList
import org.slf4j.LoggerFactory

object TradeSync {
  private val logger = LoggerFactory.getLogger("trader.trade_sync")
  var pending = false

  fun handleOffers(client: MinecraftClient, player: ClientPlayerEntity, offers: TradeOfferList) {
    for (offer in offers) {
      val enchs = EnchantmentHelper.getEnchantments(offer.sellItem)
      for (ench in enchs.enchantments) {
        val enchv = ench.value()
        val level = enchs.getLevel(ench)
        val cost = offer.firstBuyItem.count()
        val name = enchv.toString().replaceFirst("Enchantment ", "")
        val max_level = enchv.maxLevel
        val msg = "$name $level/${max_level} @ $cost"
        logger.info(msg)
        client.execute { player.sendMessage(Text.literal(msg), !Config.messageWithChat) }
        return
      }
    }
    client.execute { player.sendMessage(Text.literal("None"), !Config.messageWithChat) }
  }
}
