package xyz.fraction.setting.impl;

import xyz.fraction.module.Module;
import xyz.fraction.setting.Setting;

public class ModeSetting extends Setting {
    private final String[] options;
    private int selected;

    public ModeSetting(Module parent, String name, String[] options) {
        super(parent, name);
        this.options = options;
    }

    public void inc() {
        selected++;
        if (selected >= options.length)
            selected = 0;
    }

    public void dec() {
        selected--;
        if (selected < 0)
            selected = options.length - 1;
    }

    public void set(int i) {
        selected = i;
    }

    public String get() {
        return options[selected];
    }

    public int getSelected() {
        return selected;
    }
}
