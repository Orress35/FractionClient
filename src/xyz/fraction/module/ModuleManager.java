package xyz.fraction.module;

import xyz.fraction.module.combat.NoSwing;
import xyz.fraction.module.movement.Fly;
import xyz.fraction.module.movement.Speed;
import xyz.fraction.module.movement.Sprint;
import xyz.fraction.module.render.ClickGUI;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        /* COMBAT */
        modules.add(new NoSwing());
        /* MOVEMENT */
        modules.add(new Fly());
        modules.add(new Speed());
        modules.add(new Sprint());
        /* RENDER */
        modules.add(new ClickGUI());
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        for (Module module: modules) {
            if (module.getName().equalsIgnoreCase(name))
                return module;
        }

        return modules.get(0);
    }

    public Module getModule(Class<?> clazz) {
        for (Module module: modules) {
            if (module.getClass().getSimpleName().equals(clazz.getSimpleName()))
                return module;
        }

        return modules.get(0);
    }

    public List<Module> getCategory(Category category) {
        List<Module> modules = new ArrayList<>();

        for (Module module: getModules()) {
            if (module.getCategory() == category)
                modules.add(module);
        }

        return modules;
    }
}
