package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class FastLadder extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Normal"});
    private final DoubleSetting speed = new DoubleSetting(this, "Speed", 0.2, 1.0, 0.25);
    private final DoubleSetting downSpeed = new DoubleSetting(this, "Down Speed", 0.15, 1.0, 0.25);

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7" + mode.get());
    }

    public String getMode() {
        return mode.get();
    }

    public double getSpeed() {
        if (mode.get().equals("Normal"))
            return speed.get();
        return 0.2D;
    }

    public double getDownSpeed() {
        if (mode.get().equals("Normal"))
            return downSpeed.get();
        return 0.15D;
    }
}
