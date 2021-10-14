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

    float rotYaw, rotPitch, lastRotYaw, lastRotPitch;

    @Override
    public void onEnable() {
        if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit instanceof EntityLivingBase)
            target = (EntityLivingBase) mc.objectMouseOver.entityHit;
    }

    @Override
    public void onPre(PreMotionEvent e) {
        if (!isValid(target) || target == null) {
            target();
            return;
        }

        rotYaw = rotPitch = 0;

        float pRotYaw = Rotations.getYaw(mc.thePlayer.rotationYaw, getRotations(target.posX + 0.023, target.posY, target.posZ - 0.054, target.getEyeHeight())[0]);
        float sRotYaw = Math.signum(pRotYaw);

        if (Math.abs(pRotYaw) > 86) {
            rotYaw = Math.max(Math.abs(pRotYaw), (float) Math.random() * 10 + 20) * sRotYaw;
        } else if (Math.abs(pRotYaw) > 30) {
            rotYaw = Math.max(Math.abs(pRotYaw), (float) Math.random() * 5 + 10) * sRotYaw;
        } else if (Math.abs(pRotYaw) > 3) {
            rotYaw = Math.max(Math.abs(pRotYaw), (float) Math.random() * 6F + 2F) * sRotYaw;
        } else {
            rotYaw = 0;
        }

        switch (yawGcdMode.get()) {
            case "Value":
                rotYaw -= rotYaw % yawGcd.get();
                break;
            case "Sensitivity":
                rotYaw -= rotYaw % Rotations.gcd(yawSens.get());
                break;
            case "Legit":
                rotYaw -= rotYaw % Rotations.gcd();
                break;
        }

        mc.thePlayer.rotationYaw += rotYaw;

        if (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || mc.objectMouseOver.entityHit != target) {
            double addY = 0;
            if (Math.abs(getYawRot(mc.thePlayer.rotationYaw, getRotations(target.posX + 0.023, 0, target.posZ - 0.054, target.getEyeHeight())[0])) > 2) {
                addY = Math.random() * 0.025;
                if (Math.random() > 0.5)
                    addY *= -1;
            }

            float pRotPitch = Rotations.getPitch(mc.thePlayer.rotationPitch, getRotations(target.posX + 0.023, target.posY + addY, target.posZ - 0.054, target.getEyeHeight())[1]);
            float sRotPitch = Math.signum(pRotPitch);

            if (Math.abs(pRotPitch) > 18) {
                rotPitch = Math.max(Math.abs(pRotPitch), (float) Math.random() * 7F + 4F) * sRotPitch;
            } else {
                rotPitch = Math.max(Math.abs(pRotPitch), (float) Math.random() * 5F + 2F) * sRotPitch;
            }

            if (rotPitch == 0 && Math.abs(rotYaw) > 8 && Math.random() > 0.75) {
                if (Math.random() > 0.5)
                    rotPitch = (float) Math.random() * -0.25F;
                else
                    rotPitch = (float) Math.random() * 0.25F;
            }

            switch (pitchGcdMode.get()) {
                case "Value":
                    rotPitch -= rotPitch % pitchGcd.get();
                    break;
                case "Sensitivity":
                    rotPitch -= rotPitch % Rotations.gcd(pitchSens.get());
                    break;
                case "Legit":
                    rotPitch -= rotPitch % Rotations.gcd();
                    break;
            }

            mc.thePlayer.rotationPitch += rotPitch;
        } else {
            double vecY = mc.objectMouseOver.hitVec.yCoord;

            double diffY = vecY - target.posY - target.getEyeHeight();

            if (diffY < -0.05)
                mc.thePlayer.rotationPitch -= getPitchRot(mc.thePlayer.rotationPitch, mc.thePlayer.rotationPitch + (float) Math.random() * 3F);
            else if (diffY > 0.05)
                mc.thePlayer.rotationPitch += getPitchRot(mc.thePlayer.rotationPitch, mc.thePlayer.rotationPitch + (float) Math.random() * 3F);

            if (Math.abs(rotYaw) > 3) {
                if (Math.random() > 0.5)
                    mc.thePlayer.rotationPitch -= getPitchRot(mc.thePlayer.rotationPitch, mc.thePlayer.rotationPitch + (float) Math.random() * 0.25F);
                else
                    mc.thePlayer.rotationPitch += getPitchRot(mc.thePlayer.rotationPitch, mc.thePlayer.rotationPitch + (float) Math.random() * 0.25F);
            }
        }

        lastRotYaw = rotYaw;
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
