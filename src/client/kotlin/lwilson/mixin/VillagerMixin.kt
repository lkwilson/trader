package lwilson.mixin.client

import java.util.UUID
import lwilson.Config
import lwilson.TradeSync
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.village.VillagerProfession
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(VillagerEntity::class)
abstract class VillagerMixin {
  private val logger = LoggerFactory.getLogger("trader.villager")
  private val last_prof_map = mutableMapOf<UUID, RegistryKey<VillagerProfession>>()

  private fun handleJobChange(villager: VillagerEntity) {
    val client = MinecraftClient.getInstance()
    val player = client.player ?: return
    if (player.squaredDistanceTo(villager) > 4.5 * 4.5) {
      client.execute {
        player.sendMessage(Text.literal("Can't get trade: villager too far from player"), false)
      }
    } else if (!villager.entityWorld.isClient) {
      TradeSync.handleOffers(client, player, villager.offers)
    } else if (!client.isInSingleplayer()) {
      val xz_speed_proxy = Math.abs(player.velocity.x) + Math.abs(player.velocity.z)
      if (client.currentScreen != null) {
        client.execute {
          player.sendMessage(Text.literal("Can't get trade: player has a window open"), false)
        }
      } else if (Config.requirePlayerStationary && xz_speed_proxy > 0.001) {
        client.execute {
          player.sendMessage(
                  Text.literal("Can't get trade: getting trades would stop player movement"),
                  false
          )
        }
      } else if (Config.requireLineOfSight && !player.canSee(villager)) {
        client.execute {
          player.sendMessage(
                  Text.literal("Can't get trade: player can't see villager center"),
                  false
          )
        }
      } else if (Config.requirePlayerNotSneaking && player.isSneaking) {
        client.execute {
          player.sendMessage(
                  Text.literal("Can't get trade: getting trades would take player out of sneak"),
                  false
          )
        }
      } else {
        logger.debug("Setting pending to true")
        TradeSync.pending = true
        client.networkHandler?.sendPacket(
                PlayerInteractEntityC2SPacket.interact(villager, player.isSneaking, Hand.MAIN_HAND)
        )
      }
    }
  }

  @Inject(method = ["tick"], at = arrayOf(At("HEAD")))
  private fun onTick(info: CallbackInfo) {
    if (!Config.modEnabled) return
    val villager = this as VillagerEntity
    val vd = villager.getVillagerData()
    val prof = vd.profession().key.get()
    if (!last_prof_map.containsKey(villager.uuid)) {
      last_prof_map.put(villager.uuid, prof)
      return
    }
    val last_prof = last_prof_map.getValue(villager.uuid)
    if (prof == last_prof) return

    logger.debug("Changed profession: ${villager.uuid}: ${last_prof.value} -> ${prof.value}")
    if (last_prof == VillagerProfession.NONE && prof == VillagerProfession.LIBRARIAN) {
      handleJobChange(villager)
    }

    last_prof_map.put(villager.uuid, prof)
  }
}
