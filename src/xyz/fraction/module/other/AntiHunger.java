package xyz.fraction.module.other;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;

@ModuleInfo()
public class AntiHunger extends Module {
    @Override
    public void onPre(PreMotionEvent e) {
        e.setSprinting(false);
    }
}
