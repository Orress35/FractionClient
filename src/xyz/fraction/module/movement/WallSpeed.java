package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.util.MoveUtil;

@ModuleInfo()
public class WallSpeed extends Module {
    private final DoubleSetting multiplier = new DoubleSetting(this, "Multiplier", 1.0, 2.0, 1.0);
    private final DoubleSetting boost = new DoubleSetting(this, "Boost", 0.0, 0.5, 0.1);

    @Override
    public void onPre(PreMotionEvent e) {
        if (mc.thePlayer.isCollidedHorizontally) {
            MoveUtil.boost(boost.get());

            mc.thePlayer.motionX *= multiplier.get();
            mc.thePlayer.motionZ *= multiplier.get();
        }
    }
}
