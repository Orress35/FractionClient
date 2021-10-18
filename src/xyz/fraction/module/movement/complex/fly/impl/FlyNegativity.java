package xyz.fraction.module.movement.complex.fly.impl;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.fly.FlyBase;
import xyz.fraction.util.MoveUtil;

public class FlyNegativity extends FlyBase {
    @Override
    public void onPre(PreMotionEvent e) {
        if (!mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionX = mc.thePlayer.motionY = mc.thePlayer.motionZ = 0;
            e.setOnGround(true);
            MoveUtil.strafe(0.45);
        } else {
            e.setSneaking(false);
        }

        if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.ticksExisted % 5 == 0) {
            mc.thePlayer.setPosition(e.getX(), e.getY() + 0.5, e.getZ());
        }
    }
}
