package xyz.fraction.module.movement.complex.speed.impl;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.speed.SpeedBase;
import xyz.fraction.util.MoveUtil;

public class SpeedNegativity extends SpeedBase {
    @Override
    public void onPre(PreMotionEvent e) {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
            MoveUtil.strafe(0.45);
        }
    }
}
