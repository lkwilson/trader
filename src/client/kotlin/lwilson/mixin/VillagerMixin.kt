package lwilson.mixin

import java.util.UUID
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.registry.RegistryKey
import net.minecraft.village.VillagerProfession
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(VillagerEntity::class)
abstract class VillagerMixin {
  private val logger = LoggerFactory.getLogger("trader.villager")
  private val villager_state = mutableMapOf<UUID, RegistryKey<VillagerProfession>>()

  @Inject(method = ["tick"], at = arrayOf(At("HEAD")))
  private fun onTick(info: CallbackInfo) {
    // logger.info("tick")
    val villager = this as VillagerEntity
    val vd = villager.getVillagerData()
    val prof = vd.profession().key.get()
    if (!villager_state.containsKey(villager.uuid)) {
      villager_state.put(villager.uuid, prof)
      return
    }
    val last_state = villager_state.getValue(villager.uuid)
    if (prof != last_state) {
      logger.info("Changed profession: ${villager.uuid}: ${last_state.value} -> ${prof.value}")
      villager_state.put(villager.uuid, prof)
    }
  }
}
