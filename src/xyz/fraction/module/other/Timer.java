package xyz.fraction.module.other;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;

public class Timer extends Module {
    private final DoubleSetting timer = new DoubleSetting(this, "Timer", 0.1, 2.0, 1.0);
    private final BooleanSetting skipTick = new BooleanSetting(this, "Skip Tick", false);

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
    }

    @Override
    public void onPre(PreMotionEvent e) {
        mc.timer.timerSpeed = skipTick.get() ? (mc.thePlayer.ticksExisted % 2 == 0 ? timer.floatValue() : 1F) : 1F;
    }
}
