package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.util.MoveUtil;

@ModuleInfo()
public class Strafe extends Module {
    @Override
    public void onPre(PreMotionEvent e) {
        MoveUtil.strafe();
    }
}
