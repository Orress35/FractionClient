package xyz.fraction.module.render;

import net.minecraft.potion.Potion;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;

@ModuleInfo()
public class AntiBlind extends Module {
    @Override
    public void onPre(PreMotionEvent e) {
        if (mc.thePlayer.isPotionActive(Potion.blindness))
            mc.thePlayer.removePotionEffectClient(Potion.blindness.getId());
    }
}
