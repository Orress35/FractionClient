package xyz.fraction.module.movement.complex.speed.impl;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.speed.SpeedBase;
import xyz.fraction.util.MoveUtil;

public class SpeedVanillaHop extends SpeedBase {
    @Override
    public void onPre(PreMotionEvent e) {
        if (MoveUtil.isMoving() && e.isOnGround())
            mc.thePlayer.jump();

        MoveUtil.strafe(1);
    }
}
