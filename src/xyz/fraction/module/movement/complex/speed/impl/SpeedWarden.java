package xyz.fraction.module.movement.complex.speed.impl;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.speed.SpeedBase;
import xyz.fraction.util.MoveUtil;

public class SpeedWarden extends SpeedBase {
    @Override
    public void onPre(PreMotionEvent e) {
        if (mc.thePlayer.onGround && MoveUtil.isMoving()) {
            MoveUtil.strafe(1);
            mc.thePlayer.jump();
        } else {
            MoveUtil.strafe();
        }
    }
}
