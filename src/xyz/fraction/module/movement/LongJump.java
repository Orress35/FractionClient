package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.MoveUtil;

@ModuleInfo()
public class LongJump extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"AAC4.4.2", "NCP"});

    int airTicks = 0;
    int ticks = 0;
    boolean jumped = false;

    @Override
    public void onEnable() {
        ticks = 0;
        jumped = false;
    }

    @Override
    public void onDisable() {
        mc.thePlayer.speedInAir = 0.02F;
        mc.timer.timerSpeed = 1F;
    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7" + mode.get());

        if (mc.thePlayer.onGround) {
            airTicks = 0;
        } else {
            airTicks = Math.min(airTicks + 1, 100);
        }

        switch (mode.get()) {
            case "AAC4.4.2":
                if (airTicks > 0 && airTicks < 16) {
                    mc.thePlayer.jumpMovementFactor *= 3.5F;
                    mc.timer.timerSpeed = 0.5F;

                    if (mc.thePlayer.motionY > 0)
                        mc.thePlayer.motionY *= 1.02;
                    else
                        mc.thePlayer.motionY *= 0.98;
                } else {
                    mc.timer.timerSpeed = 0.25F;
                }
                break;
            case "NCP":
                if (MoveUtil.isMoving() && mc.thePlayer.onGround) {
                    if (jumped) {
                        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
                        toggle();
                    } else {
                        mc.thePlayer.jump();
                    }
                }

                if (airTicks == 1) {
                    MoveUtil.strafe(Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ) * 4F);
                    jumped = true;
                }

                if (!MoveUtil.isMoving())
                    mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
                break;
        }
    }
}
