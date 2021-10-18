package xyz.fraction.module.combat.complex.aura;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import xyz.fraction.Fraction;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.event.impl.PreMotionEvent;

public abstract class AuraBase {
    protected final Minecraft mc = Minecraft.getMinecraft();

    public void onPre(PreMotionEvent e) {}
    public void onPost() {}
    public void onSend(PacketEvent e) {}

    public EntityLivingBase getClosest() {
        EntityLivingBase closest = null;
        double min = 8;
        for (Entity entity: mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase e = (EntityLivingBase) entity;
                if (mc.thePlayer.getDistanceToEntity(e) < min && e != mc.thePlayer && e.getHealth() > 0 && e.isEntityAlive() && (e instanceof EntityPlayer || e instanceof EntityAnimal || e instanceof EntityMob || e instanceof EntityVillager)) {
                    closest = e;
                    min = mc.thePlayer.getDistanceToEntity(e);
                }
            }
        }

        return closest;
    }

    public Aura getAura() {
        return (Aura) Fraction.INSTANCE.getModuleManager().getModule(Aura.class);
    }

    protected float[] getRotations(double x, double y, double z, double eyeHeight) {
        double diffX = x - mc.thePlayer.posX;
        double diffY = y - mc.thePlayer.posY - mc.thePlayer.getEyeHeight() + eyeHeight;
        double diffZ = z - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[] { yaw, pitch };
    }

    protected float[] getRotations(EntityLivingBase entity) {
        return getRotations(entity.posX, entity.posY, entity.posZ, entity.getEyeHeight());
    }
}
