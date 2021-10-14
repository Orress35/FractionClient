package xyz.fraction.module.combat;

import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.DoubleSetting;

@ModuleInfo()
public class HitBox extends Module {
    private final DoubleSetting increase = new DoubleSetting(this, "Expand", 0.0, 1.0, 1.0);

    public float getExpand() {
        if (!isEnabled())
            return 0F;
        return increase.floatValue();
    }
}
