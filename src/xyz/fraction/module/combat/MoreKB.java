package xyz.fraction.module.combat;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;

@ModuleInfo()
public class MoreKB extends Module {
    int i;

    boolean backPressed = false;

    public void attack() {
        if (i > 1)
            i = 0;
    }

    @Override
    public void onPre(PreMotionEvent e) {
        if (i > 1)
            return;

        if (i == 0) {
            backPressed = mc.thePlayer.moveForward < 0;
            mc.gameSettings.keyBindBack.setPressed(true);
        } else if (i == 1) {
            mc.gameSettings.keyBindBack.setPressed(false);
        }

        i++;
    }
}
