package xyz.fraction.module.movement.complex.fly.impl;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.fly.FlyBase;
import xyz.fraction.util.MoveUtil;

public class FlyDev extends FlyBase {
    @Override
    public void onPre(PreMotionEvent e) {
        if (mc.thePlayer.isCollidedVertically) {
            mc.thePlayer.jump();
            mc.thePlayer.motionY = 0.6;
        }

        if (mc.thePlayer.motionY > 0) {
            mc.thePlayer.motionY *= 1.075;
        } else if (mc.thePlayer.ticksExisted % 2 == 0) {
            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;
            MoveUtil.strafe(8);
        } else {
            MoveUtil.strafe();
        }
    }
}
