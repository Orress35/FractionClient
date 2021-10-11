package xyz.fraction.setting.impl;

import xyz.fraction.module.Module;
import xyz.fraction.setting.Setting;

public class DoubleSetting extends Setting {
    private final double min, max;
    private double value;

    public DoubleSetting(Module parent, String name, double min, double max, double value) {
        super(parent, name);
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double get() {
        return value;
    }

    public void set(double value) {
        this.value = value;
    }

    public float floatValue() {
        return (float) value;
    }

    public int intValue() {
        return (int) value;
    }
}
