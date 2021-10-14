package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class Eagle extends Module {
    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.setPressed(false);
    }

    @Override
    public void onPre(PreMotionEvent e) {
        boolean edge = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().expand(-0.25, 0, -0.25).offset(0, -1, 0)).isEmpty();
        mc.gameSettings.keyBindSneak.setPressed(edge && mc.thePlayer.onGround);
    }
}
