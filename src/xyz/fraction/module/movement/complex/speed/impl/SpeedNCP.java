package xyz.fraction.module.movement.complex.speed.impl;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.speed.SpeedBase;
import xyz.fraction.util.MoveUtil;

public class SpeedNCP extends SpeedBase {
    int moving = 0;

    @Override
    public void onPre(PreMotionEvent e) {
        if (MoveUtil.isMoving())
            moving++;
        else
            moving = 0;

        if (MoveUtil.isMoving() && e.isOnGround())
            mc.thePlayer.jump();

        if (moving > 50)
            mc.thePlayer.jumpMovementFactor *= 1.075F;
        else
            mc.thePlayer.jumpMovementFactor *= 1.025F;

        mc.timer.timerSpeed = 1.075F;

        MoveUtil.strafe();
    }
}
