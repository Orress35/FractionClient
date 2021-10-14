package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class Spider extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla"});
    private final DoubleSetting vanillaSpeed = new DoubleSetting(this, "Vanilla Speed", 0.1, 1.0, 0.2);

    @Override
    public void onPre(PreMotionEvent e) {
        if (mc.thePlayer.isCollidedHorizontally) {
            switch (mode.get()) {
                case "Vanilla":
                    mc.thePlayer.motionY = vanillaSpeed.get();
                    break;
            }
        }
    }
}
