package xyz.fraction.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
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
public class Aura extends Module {
    private final ModeSetting targetOn = new ModeSetting(this, "Target On", new String[] {"Pre", "Post"});
    private final DoubleSetting targetRange = new DoubleSetting(this, "Target Range", 3.0, 25.0, 8.0);

    /* ATTACKING */
    private final BooleanSetting legitHits = new BooleanSetting(this, "Legit Hits", false);

    private final ModeSetting hitOn = new ModeSetting(this, "Hit On", new String[] {"Pre", "Post"});
    private final DoubleSetting hitRange = new DoubleSetting(this, "Hit Range", 3.0, 6.0, 3.0);

    private final BooleanSetting swingBefore = new BooleanSetting(this, "Swing Before", false);
    private final BooleanSetting swingAfter = new BooleanSetting(this, "Swing After", false);
    private final DoubleSetting swingRange = new DoubleSetting(this, "Swing Range", 3.0, 25.0, 8.0);

    /* ROTATIONS */
    private final ModeSetting yawGcdMode = new ModeSetting(this, "Yaw Gcd Mode", new String[] {"Value", "Sensitivity", "Legit", "None"});
    private final DoubleSetting yawGcd = new DoubleSetting(this, "Yaw Gcd", 0.05, 1.0, 0.1);
    private final IntSetting yawSens = new IntSetting(this, "Yaw Sensitivity", 1, 200, 100);

    private final ModeSetting pitchGcdMode = new ModeSetting(this, "Pitch Gcd Mode", new String[] {"Value", "Sensitivity", "Legit", "None"});
    private final DoubleSetting pitchGcd = new DoubleSetting(this, "Pitch Gcd", 0.05, 1.0, 0.1);
    private final IntSetting pitchSens = new IntSetting(this, "Pitch Sensitivity", 1, 200, 100);

    private final BooleanSetting alwaysC06 = new BooleanSetting(this, "Always C06", false);

    private EntityLivingBase target;

    private final int[] delays = new int[] {1,2,2,1,2,3,2,3,2,2,3,2,1,2,2,1,2,2,3,2,1,2,2,3,2,2,3,2,2,1,2,2,2,1,2,2,1,2,2,2,1,2,2,2,1,2,2,2,5,2,2,1,2,2,2,3,2,1,2,2,2,3,2,2,2,2,1,2,2,1,2,2,3,2,2,2,1,2,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,3,2,2,3,2,2,1,2,2,2,2,2,2,1,2,2,2,2,3,2,2,2,5,2,2,2,2,1,2,2,2,1,2,2,2,2,2,2,2,2,1,2,2,2,1,2,2,2,3,2,2,2,1,2,2,2,2,2,1,2,2,2,2,1,2,2,2,3,2,3,2,2,2,2,2,2,1,2,4,2,2,1,2,2,2,2,2,2,2,3,2,2,2,2,2,2,2,1,2,2,2,2,2,3,2,2,2,2,2,2,3,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,2,2,2,2,2,2,2,2,1,2,5,2,2,2};
    private int clickTicks = 0;
    private int index = 0;

    @Override
    public void onSend(PacketEvent e) {
         /* code for getting click patterns
        if (e.getPacket() instanceof C0APacketAnimation) {
            System.out.print(clickTicks + ",");
            clickTicks = 0;
        }
         */
    }

    @Override
    public void onPre(PreMotionEvent e) {
        clickTicks = Math.min(clickTicks + 1, 50);

        if ((!isValid(target) || target == null) && targetOn.get().equals("Pre")) {
            target();
            return;
        }

        if (target == null)
            return;

        float[] rotations = getRotations(target.posX, target.posY, target.posZ, target.getEyeHeight());
        float perfectYaw = rotations[0];
        float perfectPitch = rotations[1];

        mc.thePlayer.rotationYaw += getYawRot(mc.thePlayer.rotationYaw, perfectYaw);
        mc.thePlayer.rotationPitch += getPitchRot(mc.thePlayer.rotationPitch, perfectPitch);

        if (legitHits.get()) {
            click();
        } else if (hitOn.get().equals("Pre")) {
            attack();
        }

        if (alwaysC06.get())
            e.setPosLook(true);
    }

    @Override
    public void onPost() {
        if ((!isValid(target) || target == null) && targetOn.get().equals("Post")) {
            target();
            return;
        }

        if (target == null)
            return;

        if (hitOn.get().equals("Post")) {
            attack();
        }
    }

    public void target() {
        double closest = targetRange.get();
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

    public void click() {
        if (clickTicks < delays[index]) {
            return;
        }

        clickTicks = 0;

        index++;

        if (index >= delays.length)
            index = (int) (Math.random() * 8);

        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            mc.gameSettings.keyBindAttack.setPressTime(1);
        } else {
            mc.thePlayer.swingItem();
        }
    }

    public void attack() {
        if (clickTicks < delays[index]) {
            return;
        }

        clickTicks = 0;

        index++;

        if (index >= delays.length)
            index = (int) (Math.random() * 10);

        if (swingBefore.get() && mc.thePlayer.getDistanceToEntity(target) <= swingRange.get())
            mc.thePlayer.swingItem();

        if (mc.thePlayer.getDistanceToEntity(target) <= hitRange.get())
            mc.playerController.attackEntity(mc.thePlayer, target);

        if (swingAfter.get() && mc.thePlayer.getDistanceToEntity(target) <= swingRange.get())
            mc.thePlayer.swingItem();
    }

    public boolean isValid(EntityLivingBase entity) {
        return entity != null && mc.theWorld.loadedEntityList.contains(entity) && mc.thePlayer.getDistanceToEntity(entity) < targetRange.get() && entity.getHealth() > 0 && entity.isEntityAlive() && entity != mc.thePlayer;
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
