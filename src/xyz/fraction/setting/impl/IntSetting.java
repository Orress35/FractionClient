package xyz.fraction.setting.impl;

import xyz.fraction.module.Module;
import xyz.fraction.setting.Setting;

public class IntSetting extends Setting {
    private final int min, max;
    private int value;

    public IntSetting(Module parent, String name, int min, int max, int value) {
        super(parent, name);
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }

    public float floatValue() {
        return (float) value;
    }
}
