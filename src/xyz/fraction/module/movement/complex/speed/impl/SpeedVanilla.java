package xyz.fraction.module.movement.complex.speed.impl;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.speed.SpeedBase;
import xyz.fraction.util.MoveUtil;

public class SpeedVanilla extends SpeedBase {
    @Override
    public void onPre(PreMotionEvent e) {
        MoveUtil.strafe(1);
    }
}
