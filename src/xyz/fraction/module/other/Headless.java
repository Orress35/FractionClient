package xyz.fraction.module.other;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;

@ModuleInfo()
public class Headless extends Module {
    @Override
    public void onPre(PreMotionEvent e) {
        e.setPitch(-150F);
    }
}
