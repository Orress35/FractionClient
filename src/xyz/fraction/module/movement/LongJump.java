package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.MoveUtil;

@ModuleInfo()
public class LongJump extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"NCP"});

    int airTicks = 0;
    int ticks = 0;
    boolean jumped = false;

    @Override
    public void onEnable() {
        ticks = 0;
        jumped = false;
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
