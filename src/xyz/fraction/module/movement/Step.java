package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class Step extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla"});
    private final DoubleSetting height = new DoubleSetting(this, "Height", 0.25, 5.0, 1.0);

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6F;
    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7" + mode.get());
        switch (mode.get()) {
            case "Vanilla":
                mc.thePlayer.stepHeight = height.floatValue();
                break;
        }
    }
}
