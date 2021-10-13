package xyz.fraction.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
    private final BooleanSetting silentRotations = new BooleanSetting(this, "Silent Rotations", false);

    private final ModeSetting yawGcdMode = new ModeSetting(this, "Yaw Gcd Mode", new String[] {"Value", "Sensitivity", "Legit", "None"});
    private final DoubleSetting yawGcd = new DoubleSetting(this, "Yaw Gcd", 0.05, 1.0, 0.1);
    private final IntSetting yawSens = new IntSetting(this, "Yaw Sensitivity", 1, 200, 100);

    private final ModeSetting pitchGcdMode = new ModeSetting(this, "Pitch Gcd Mode", new String[] {"Value", "Sensitivity", "Legit", "None"});
    private final DoubleSetting pitchGcd = new DoubleSetting(this, "Pitch Gcd", 0.05, 1.0, 0.1);
    private final IntSetting pitchSens = new IntSetting(this, "Pitch Sensitivity", 1, 200, 100);

    private EntityLivingBase target;

    @Override
    public void onPre(PreMotionEvent e) {
        if ((!isValid(target) || target == null) && targetOn.get().equals("Pre")) {
            target();
            return;
        }

        if (target == null)
            return;

        if (legitHits.get()) {
            click();
        } else if (hitOn.get().equals("Pre")) {
            attack();
        }
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

    }

    public void attack() {
        if (swingBefore.get() && mc.thePlayer.getDistanceToEntity(target) <= swingRange.get())
            mc.thePlayer.swingItem();

        if (mc.thePlayer.getDistanceToEntity(target) <= hitRange.get())
            mc.playerController.attackEntity(mc.thePlayer, target);

        if (swingAfter.get() && mc.thePlayer.getDistanceToEntity(target) <= swingRange.get())
            mc.thePlayer.swingItem();
    }

    public boolean isValid(EntityLivingBase entity) {
        return entity != null && mc.theWorld.loadedEntityList.contains(target) && mc.thePlayer.getDistanceToEntity(target) < targetRange.get() && target.getHealth() > 0 && target.isEntityAlive() && target != mc.thePlayer;
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
}
