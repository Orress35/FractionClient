package xyz.fraction.module.movement.complex.fly.impl;

import org.lwjgl.input.Keyboard;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.fly.FlyBase;
import xyz.fraction.util.MoveUtil;

public class FlyWarden extends FlyBase {
    @Override
    public void onPre(PreMotionEvent e) {
        mc.timer.timerSpeed = 1F;

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            mc.timer.timerSpeed = 0.25F;

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            mc.timer.timerSpeed = 5F;

        if (mc.thePlayer.ticksExisted % 2 == 0) {
            mc.thePlayer.motionY *= 0.75;
            MoveUtil.strafe(9.5);
        } else {
            MoveUtil.strafe();
        }

        e.setSneaking(false);
    }
}
