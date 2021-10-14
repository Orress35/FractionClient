package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class Sprint extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Legit", "Basic"});

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSprint.setPressed(false);
    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7" + mode.get());
        if (mode.get().equals("Legit"))
            mc.gameSettings.keyBindSprint.setPressed(true);
        else
            mc.thePlayer.setSprinting(true);
    }
}
