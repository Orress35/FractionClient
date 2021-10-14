package xyz.fraction.module.render;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;

@ModuleInfo()
public class FullBright extends Module {
    @Override
    public void onPre(PreMotionEvent e) {
        mc.gameSettings.gamma = 1000F;
    }
}
