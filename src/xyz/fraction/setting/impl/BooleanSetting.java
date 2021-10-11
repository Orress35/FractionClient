package xyz.fraction.setting.impl;

import xyz.fraction.module.Module;
import xyz.fraction.setting.Setting;

public class BooleanSetting extends Setting {
    private boolean value;

    public BooleanSetting(Module parent, String name, boolean value) {
        super(parent, name);
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    public void set(boolean value) {
        this.value = value;
    }

    public void toggle() {
        value = !value;
    }
}
