package xyz.fraction.module.other;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.Rotations;

@ModuleInfo()
public class Derp extends Module {
    private final BooleanSetting rotateYaw = new BooleanSetting(this, "Rotate Yaw", true);

    private final ModeSetting yawGcdMode = new ModeSetting(this, "Yaw Gcd Mode", new String[] {"Value", "Sensitivity", "Legit", "None"});
    private final DoubleSetting yawGcd = new DoubleSetting(this, "Yaw Gcd", 0.05, 1.0, 0.1);
    private final IntSetting yawSens = new IntSetting(this, "Yaw Sensitivity", 1, 200, 100);

    private final BooleanSetting rotatePitch = new BooleanSetting(this, "Rotate Pitch", true);

    private final ModeSetting pitchGcdMode = new ModeSetting(this, "Pitch Gcd Mode", new String[] {"Value", "Sensitivity", "Legit", "None"});
    private final DoubleSetting pitchGcd = new DoubleSetting(this, "Pitch Gcd", 0.05, 1.0, 0.1);
    private final IntSetting pitchSens = new IntSetting(this, "Pitch Sensitivity", 1, 200, 100);

    @Override
    public void onPre(PreMotionEvent e) {
        if (rotateYaw.get())
            e.setYaw(e.getYaw() + getYawRot(e.getYaw(), (float) Math.random() * 360F));

        if (rotatePitch.get())
            e.setPitch(e.getPitch() + getPitchRot(e.getPitch(), (float) Math.random() * 180F - 90F));
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
