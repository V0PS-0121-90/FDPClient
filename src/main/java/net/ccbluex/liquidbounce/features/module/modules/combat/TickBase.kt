/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.event.AttackEvent
import net.ccbluex.liquidbounce.event.TickEvent
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.minecraft.entity.EntityLivingBase

@ModuleInfo(name = "TickBase", category = ModuleCategory.COMBAT)
class TickBase : Module() {

    private var ticks = 0

    private val ticksAmount = IntegerValue("BoostTicks", 10, 3, 20)
    private val BoostAmount = FloatValue("BoostTimer", 10f, 1f, 50f)
    private val ChargeAmount = FloatValue("ChargeTimer", 0.11f, 0.05f, 1f)

    @EventTarget
    fun onAttack(event: AttackEvent) {
        if (event.targetEntity is EntityLivingBase && ticks == 0) {
            ticks = ticksAmount.get()
        }
    }

    override fun onEnable() {
        mc.timer.timerSpeed = 1f
    }

    override fun onDisable() {
        mc.timer.timerSpeed = 1f
    }


    @EventTarget
    fun onTick(event: TickEvent) {
        if (ticks == ticksAmount.get()) {
            mc.timer.timerSpeed = ChargeAmount.get()
            ticks --
        } else if (ticks > 1) {
            mc.timer.timerSpeed = BoostAmount.get()
            ticks --
        } else if (ticks == 1) {
            mc.timer.timerSpeed = 1f
            ticks --
        }
    }


}
