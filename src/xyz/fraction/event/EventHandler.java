package xyz.fraction.event;

import xyz.fraction.Fraction;
import xyz.fraction.event.impl.JumpEvent;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;

public class EventHandler {
    public void onPre(PreMotionEvent e) {
        for (Module module: Fraction.INSTANCE.getModuleManager().getModules()) {
            if (module.isEnabled())
                module.onPre(e);
        }
    }

    public void onJump(JumpEvent e) {
        for (Module module: Fraction.INSTANCE.getModuleManager().getModules()) {
            if (module.isEnabled())
                module.onJump(e);
        }
    }

    public void onRender() {
        for (Module module: Fraction.INSTANCE.getModuleManager().getModules()) {
            if (module.isEnabled())
                module.onRender();
        }
    }

    public void onPost() {
        for (Module module: Fraction.INSTANCE.getModuleManager().getModules()) {
            if (module.isEnabled())
                module.onPost();
        }
    }

    public void onUpdate() {
        for (Module module: Fraction.INSTANCE.getModuleManager().getModules()) {
            if (module.isEnabled())
                module.onUpdate();
        }
    }

    public void onKey(int key) {
        for (Module module: Fraction.INSTANCE.getModuleManager().getModules())
            module.onKey(key);
    }

    public void onSend(PacketEvent e) {
        for (Module module: Fraction.INSTANCE.getModuleManager().getModules()) {
            if (module.isEnabled())
                module.onSend(e);
        }
    }

    public void onReceive(PacketEvent e) {
        for (Module module: Fraction.INSTANCE.getModuleManager().getModules()) {
            if (module.isEnabled())
                module.onReceive(e);
        }
    }
}
