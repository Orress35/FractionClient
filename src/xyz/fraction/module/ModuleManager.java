package xyz.fraction.module;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {

    }

    public List<Module> getModules() {
        return modules;
    }
}
