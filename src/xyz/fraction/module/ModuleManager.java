package xyz.fraction.module;

import xyz.fraction.module.combat.Aura;
import xyz.fraction.module.combat.NoSwing;
import xyz.fraction.module.movement.*;
import xyz.fraction.module.other.AntiFalse;
import xyz.fraction.module.other.Derp;
import xyz.fraction.module.render.ClickGUI;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        /* COMBAT */
        modules.add(new Aura());
        modules.add(new NoSwing());

        /* MOVEMENT */
        modules.add(new Fly());
        modules.add(new HighJump());
        modules.add(new NoSlow());
        modules.add(new Speed());
        modules.add(new Sprint());

        /* OTHER */
        modules.add(new AntiFalse());
        modules.add(new Derp());

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
