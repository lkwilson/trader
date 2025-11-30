package lwilson.mixin

import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.entity.Entity
import net.minecraft.text.Text
import org.slf4j.LoggerFactory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.data.DataTracker

@Mixin(Entity::class)
abstract class EntityDataTrackerMixin {
    private val logger = LoggerFactory.getLogger("trader.datatracker")
    
    @Inject(method = ["onDataTrackerUpdate"], at = arrayOf(At("TAIL")))
    private fun onDataTrackerUpdate(entry: DataTracker.Entry<*>, info: CallbackInfo) {
        if (this !is VillagerEntity) return
        
        val villager = this as VillagerEntity
        logger.info("VillagerEntity DataTracker updated")
        logger.info("Profession: ${villager.villagerData.profession}")
    }
}