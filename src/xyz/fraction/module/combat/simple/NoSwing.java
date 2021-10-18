package xyz.fraction.module.combat.simple;

import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;

@ModuleInfo()
public class NoSwing extends Module {
    private final BooleanSetting hitting = new BooleanSetting(this, "Htting", true);
    private final BooleanSetting placing = new BooleanSetting(this, "Placing", true);
    private final BooleanSetting breaking = new BooleanSetting(this, "Breaking", true);

    public boolean getHitting() {
        return hitting.get();
    }

    public boolean getPlacing() {
        return placing.get();
    }

    public boolean getBreaking() {
        return breaking.get();
    }
}
