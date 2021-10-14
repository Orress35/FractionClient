package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.DoubleSetting;

@ModuleInfo()
public class AutoWalk extends Module {
    @Override
    public void onDisable() {
        mc.gameSettings.keyBindForward.setPressed(false);
    }

    @Override
    public void onPre(PreMotionEvent e) {
        mc.gameSettings.keyBindForward.setPressed(true);
    }
}
