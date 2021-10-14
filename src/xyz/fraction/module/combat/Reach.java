package xyz.fraction.module.combat;

import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.DoubleSetting;

@ModuleInfo()
public class Reach extends Module {
    private final DoubleSetting min = new DoubleSetting(this, "Min", 3.0, 6.0, 3.0);
    private final DoubleSetting max = new DoubleSetting(this, "Max", 3.0, 6.0, 3.0);

    public double getReach() {
        if (min.get() > max.get())
            min.set(max.get());
        if (!isEnabled())
            return 3.0D;
        return Math.random() * (max.get() - min.get()) + min.get();
    }
}
