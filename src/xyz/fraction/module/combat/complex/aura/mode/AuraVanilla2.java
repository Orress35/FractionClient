package xyz.fraction.module.combat.complex.aura.mode;

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

public class AuraVanilla2 extends AuraBase {
    @Override
    public void onPost() {
        mc.gameSettings.keyBindUse.setPressed(Mouse.isButtonDown(1));

        EntityLivingBase target = getClosest();
        if (target == null)
            return;

        mc.gameSettings.keyBindUse.setPressed(true);
        mc.playerController.attackEntity(mc.thePlayer, target);
        NoSwing noSwing = (NoSwing) Fraction.INSTANCE.getModuleManager().getModule(NoSwing.class);
        if (!noSwing.isEnabled() || !noSwing.getHitting())
            mc.thePlayer.swingItem();
    }
}
