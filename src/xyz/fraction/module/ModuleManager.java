package xyz.fraction.module;

import xyz.fraction.module.movement.Fly;
import xyz.fraction.module.movement.Sprint;
import xyz.fraction.module.render.ClickGUI;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        /* MOVEMENT */
        modules.add(new Fly());
        modules.add(new Sprint());
        /* RENDER */
        modules.add(new ClickGUI());
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        for (Module module: modules) {
            if (module.getName().equalsIgnoreCase(name))
                return module;
        }

        return modules.get(0);
    }

    public List<Module> getModulesByCategory(Category category) {
        List<Module> modules = new ArrayList<>();

        for (Module module: getModules()) {
            if (module.getCategory() == category)
                modules.add(module);
        }

        return modules;
    }
}
