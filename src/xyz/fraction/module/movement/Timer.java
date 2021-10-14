package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.DoubleSetting;

@ModuleInfo()
public class Timer extends Module {
    private final DoubleSetting timer = new DoubleSetting(this, "Timer", 0.1, 2.5, 1.0);

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
    }

    @Override
    public void onPre(PreMotionEvent e) {
        mc.timer.timerSpeed = timer.floatValue();
    }
}
