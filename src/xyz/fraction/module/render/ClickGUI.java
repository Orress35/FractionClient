package xyz.fraction.module.render;

import org.lwjgl.input.Keyboard;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;

@ModuleInfo(key = Keyboard.KEY_RSHIFT)
public class ClickGUI extends Module {
    @Override
    public void onEnable() {
        mc.displayGuiScreen(new xyz.fraction.ui.ClickGUI());
        toggle();
    }
}
