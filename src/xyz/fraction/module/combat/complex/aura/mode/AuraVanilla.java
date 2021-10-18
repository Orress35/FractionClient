package xyz.fraction.module.combat.complex.aura.mode;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;
import xyz.fraction.Fraction;
import xyz.fraction.module.combat.complex.aura.AuraBase;
import xyz.fraction.module.combat.simple.NoSwing;

public class AuraVanilla extends AuraBase {
    @Override
    public void onPost() {
        mc.gameSettings.keyBindUse.setPressed(Mouse.isButtonDown(1));

        int i = 0;
        boolean hit = false;
        for (Entity entity: mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase e = (EntityLivingBase) entity;
                if (mc.thePlayer.getDistanceToEntity(e) < 8 && e != mc.thePlayer && e.getHealth() > 0 && e.isEntityAlive() && (e instanceof EntityPlayer || e instanceof EntityAnimal || e instanceof EntityMob || e instanceof EntityVillager)) {
                    mc.gameSettings.keyBindUse.setPressed(true);
                    mc.playerController.attackEntity(mc.thePlayer, e);
                    hit = true;
                    if (++i > 2)
                        break;
                }
            }
        }

        if (hit) {
            NoSwing noSwing = (NoSwing) Fraction.INSTANCE.getModuleManager().getModule(NoSwing.class);
            if (!noSwing.isEnabled() || !noSwing.getHitting())
                mc.thePlayer.swingItem();
        }
    }
}
