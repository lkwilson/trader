package lwilson.mixin

import java.util.UUID
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text
import net.minecraft.village.VillagerProfession
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantment
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import kotlin.jvm.optionals.getOrDefault

@Mixin(VillagerEntity::class)
abstract class VillagerMixin {
  private val logger = LoggerFactory.getLogger("trader.villager")
  private val last_prof_map = mutableMapOf<UUID, RegistryKey<VillagerProfession>>()

  private fun handleJobChange(villager: VillagerEntity) {
    val client = MinecraftClient.getInstance()
    if (!villager.entityWorld.isClient) {
      val player = client.player ?: return
      for (offer in villager.offers) {
        val enchs = EnchantmentHelper.getEnchantments(offer.sellItem)
        for (ench in enchs.enchantments) {
          val enchv = ench.value()
          val level = enchs.getLevel(ench)
          val cost = offer.firstBuyItem.count()
          val name = enchv.toString().replaceFirst("Enchantment ", "")
          val max_level = enchv.maxLevel
          val msg = "$name $level (max: $max_level) FOR $cost"
          // logger.info(msg)
          player.sendMessage(Text.literal(msg), true)
          return
        }
      }
      player.sendMessage(Text.literal("Enchantment None"), true)
    } else if (!client.isInSingleplayer()) {
      logger.info("In server world. Need to request offers from server")
    }
  }

  @Inject(method = ["tick"], at = arrayOf(At("HEAD")))
  private fun onTick(info: CallbackInfo) {
    // logger.info("tick")
    val villager = this as VillagerEntity
    val vd = villager.getVillagerData()
    val prof = vd.profession().key.get()
    if (!last_prof_map.containsKey(villager.uuid)) {
      last_prof_map.put(villager.uuid, prof)
      return
    }
    val last_prof = last_prof_map.getValue(villager.uuid)
    if (prof == last_prof) return

    logger.info("Changed profession: ${villager.uuid}: ${last_prof.value} -> ${prof.value}")
    if (last_prof == VillagerProfession.NONE && prof == VillagerProfession.LIBRARIAN) {
      handleJobChange(villager)
    }

    last_prof_map.put(villager.uuid, prof)
  }
}
