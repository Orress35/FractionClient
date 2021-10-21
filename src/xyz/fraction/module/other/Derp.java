package xyz.fraction.module.other;

import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;

@ModuleInfo()
public class Derp extends Module {
    @Override
    public void onPre(PreMotionEvent e) {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(e.getX(), e.getY(), e.getZ(), (float) Math.random() * 360F, (float) Math.random() * 180F - 90F, e.isOnGround()));
    }
}
