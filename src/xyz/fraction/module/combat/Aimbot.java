package xyz.fraction.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.Rotations;

@ModuleInfo()
public class Aimbot extends Module {
    private final DoubleSetting range = new DoubleSetting(this, "Range", 3.0, 25.0, 8.0);

    /* ROTATIONS */
    private final ModeSetting yawGcdMode = new ModeSetting(this, "Yaw Gcd Mode", new String[] {"Value", "Sensitivity", "Legit", "None"});
    private final DoubleSetting yawGcd = new DoubleSetting(this, "Yaw Gcd", 0.05, 1.0, 0.1);
    private final IntSetting yawSens = new IntSetting(this, "Yaw Sensitivity", 1, 200, 100);

    private final ModeSetting pitchGcdMode = new ModeSetting(this, "Pitch Gcd Mode", new String[] {"Value", "Sensitivity", "Legit", "None"});
    private final DoubleSetting pitchGcd = new DoubleSetting(this, "Pitch Gcd", 0.05, 1.0, 0.1);
    private final IntSetting pitchSens = new IntSetting(this, "Pitch Sensitivity", 1, 200, 100);

    private EntityLivingBase target;

    @Override
    public void onPre(PreMotionEvent e) {
        if (!isValid(target) || target == null) {
            target();
            return;
        }

        float[] rotations = getRotations(target.posX, target.posY, target.posZ, target.getEyeHeight());
        float perfectYaw = rotations[0];
        float perfectPitch = rotations[1];

        mc.thePlayer.rotationYaw += getYawRot(mc.thePlayer.rotationYaw, perfectYaw);
        mc.thePlayer.rotationPitch += getPitchRot(mc.thePlayer.rotationPitch, perfectPitch);
    }

    public void target() {
        double closest = range.get();
        for (Entity entity: mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase e = (EntityLivingBase) entity;

                if (isValid(e) && mc.thePlayer.getDistanceToEntity(e) <= closest) {
                    closest = mc.thePlayer.getDistanceToEntity(e);
                    target = e;
                }
            }
        }
    }

    public boolean isValid(EntityLivingBase entity) {
        return entity != null && mc.theWorld.loadedEntityList.contains(entity) && mc.thePlayer.getDistanceToEntity(entity) < range.get() && entity.getHealth() > 0 && entity.isEntityAlive() && entity != mc.thePlayer;
    }

    public float getYawRot(float player, float perfect) {
        float rotation = Rotations.getYaw(player, perfect);

        switch (yawGcdMode.get()) {
            case "Value":
                rotation -= rotation % yawGcd.get();
                break;
            case "Sensitivity":
                rotation -= rotation % Rotations.gcd(yawSens.get());
                break;
            case "Legit":
                rotation -= rotation % Rotations.gcd();
                break;
        }

        return rotation;
    }

    public float getPitchRot(float player, float perfect) {
        float rotation = Rotations.getYaw(player, perfect);

        switch (pitchGcdMode.get()) {
            case "Value":
                rotation -= rotation % pitchGcd.get();
                break;
            case "Sensitivity":
                rotation -= rotation % Rotations.gcd(pitchSens.get());
                break;
            case "Legit":
                rotation -= rotation % Rotations.gcd();
                break;
        }

        return rotation;
    }

    private float[] getRotations(double x, double y, double z, double eyeHeight) {
        double diffX = x - mc.thePlayer.posX;
        double diffY = y - mc.thePlayer.posY - mc.thePlayer.getEyeHeight() + eyeHeight;
        double diffZ = z - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[] { yaw, pitch };
    }
}
