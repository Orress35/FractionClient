package xyz.fraction.module;

import xyz.fraction.module.combat.*;
import xyz.fraction.module.movement.*;
import xyz.fraction.module.render.AntiBlind;
import xyz.fraction.module.other.AntiFalse;
import xyz.fraction.module.other.AntiHunger;
import xyz.fraction.module.other.Derp;
import xyz.fraction.module.render.ClickGUI;
import xyz.fraction.module.render.FullBright;
import xyz.fraction.module.testing.ReachAlert;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        /* COMBAT */
        modules.add(new Aimbot());
        modules.add(new Aura());
        modules.add(new Clicker());
        modules.add(new HitBox());
        modules.add(new NoSwing());
        modules.add(new Reach());
        modules.add(new Triggerbot());

        /* MOVEMENT */
        modules.add(new AirJump());
        modules.add(new AntiVoid());
        modules.add(new AutoWalk());
        modules.add(new Eagle());
        modules.add(new FastLadder());
        modules.add(new Fly());
        modules.add(new HighJump());
        modules.add(new LongJump());
        modules.add(new NoRotate());
        modules.add(new NoSlow());
        modules.add(new ReverseStep());
        modules.add(new Sneak());
        modules.add(new Speed());
        modules.add(new Spider());
        modules.add(new Sprint());
        modules.add(new Step());
        modules.add(new Strafe());
        modules.add(new Timer());
        modules.add(new WallSpeed());

        /* OTHER */
        modules.add(new AntiFalse());
        modules.add(new AntiHunger());
        modules.add(new Derp());

        /* RENDER */
        modules.add(new AntiBlind());
        modules.add(new ClickGUI());
        modules.add(new FullBright());

        /* TESTING */
        modules.add(new ReachAlert());
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
