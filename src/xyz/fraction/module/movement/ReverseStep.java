package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class ReverseStep extends Module {
    private final DoubleSetting motion = new DoubleSetting(this, "Motion", 0.25, 5.0, 1.0);

    @Override
    public void onPre(PreMotionEvent e) {
        if (!mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && !mc.thePlayer.isInWeb && mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically)
            mc.thePlayer.motionY = -motion.get();
    }
}
