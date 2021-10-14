package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.MoveUtil;

@ModuleInfo()
public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla", "ACR", "ACR Hop"});
    private final DoubleSetting vanillaSpeed = new DoubleSetting(this, "Vanilla Speed", 0.1, 2.5, 1.0);
    private final BooleanSetting vanillaJump = new BooleanSetting(this, "Vanilla Jump", false);

    int airTicks = 0;

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7" + mode.get());

        if (mc.thePlayer.onGround)
            airTicks = 0;
        else
            airTicks = Math.min(airTicks + 1, 100);

        switch (mode.get()) {
            case "Vanilla":
                if (vanillaJump.get() && MoveUtil.isMoving() && e.isOnGround())
                    mc.thePlayer.jump();
                MoveUtil.strafe(vanillaSpeed.get());
                break;
            case "ACR":
                MoveUtil.strafe(0.35);
                break;
            case "ACR Hop":
                if (MoveUtil.isMoving() && e.isOnGround())
                    mc.thePlayer.jump();

                if (airTicks > 0) {
                    double motion = Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ) + 0.032F;
                    double limit = 0.36 * Math.pow(0.985, airTicks + 1);
                    while (motion > limit * 0.8 && motion < limit && MoveUtil.isMoving()) {
                        MoveUtil.boost(0.005);

                        motion = Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ) + 0.032F;
                    }
                }

                break;
        }
    }
}
