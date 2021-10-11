package xyz.fraction.setting;

import xyz.fraction.module.Module;

public class Setting {
    private final Module parent;
    private final String name;
    protected String displayName;

    public Setting(Module parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public Module getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }
}
