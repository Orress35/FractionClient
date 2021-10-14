package xyz.fraction.module.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class AntiVoid extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"NCP"});

    @Override
    public void onPre(PreMotionEvent e) {
        switch (mode.get()) {
            case "NCP":
                if (mc.thePlayer.fallDistance > 3F) {
                    mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.01, mc.thePlayer.posZ, false));
                    mc.thePlayer.fallDistance = 0F;
                }

                break;
        }
    }
}
