package xyz.fraction.module.movement.complex.fly.impl;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.fly.FlyBase;
import xyz.fraction.util.MoveUtil;

public class FlyVanilla extends FlyBase {
    @Override
    public void onPre(PreMotionEvent e) {
        mc.thePlayer.motionX = mc.thePlayer.motionY = mc.thePlayer.motionZ = 0;

        if (mc.gameSettings.keyBindJump.isKeyDown())
            mc.thePlayer.motionY++;

        if (mc.gameSettings.keyBindSneak.isKeyDown())
            mc.thePlayer.motionY--;

        MoveUtil.strafe(1);
    }
}
