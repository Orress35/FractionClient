package xyz.fraction.module.other;

import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class NoFall extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"GroundSpoof", "Packet", "Reduce"});

    @Override
    public void onPre(PreMotionEvent e) {
        switch (mode.get()) {
            case "GroundSpoof":
                e.setOnGround(true);
                break;
            case "Packet":
                if (mc.thePlayer.fallDistance > 3F) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                    mc.thePlayer.fallDistance = 0F;
                }
                break;
            case "Reduce":
                if (mc.thePlayer.fallDistance > 3F) {
                    mc.thePlayer.onGround = true;
                    mc.thePlayer.fallDistance = 0F;
                }
                break;
        }
    }
}
