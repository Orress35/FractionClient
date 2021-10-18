package xyz.fraction.module.other;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.setting.impl.BooleanSetting;

public class Disabler extends Module {
    private final BooleanSetting keepAlive = new BooleanSetting(this, "KeepAlive", false);
    private final BooleanSetting voidTp = new BooleanSetting(this, "VoidTp", false);

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
    }

    long lastVoidTp = System.currentTimeMillis();

    @Override
    public void onPre(PreMotionEvent e) {
        if (voidTp.get()) {
            if (System.currentTimeMillis() - lastVoidTp > 1000L) {
                mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(e.getX(), -5000, e.getZ(), true));
                lastVoidTp = System.currentTimeMillis();
            }

            mc.timer.timerSpeed = 0.95F;
        }
    }

    @Override
    public void onSend(PacketEvent e) {
        if (e.getPacket() instanceof C00PacketKeepAlive && keepAlive.get())
            ((C00PacketKeepAlive) e.getPacket()).setKey(Integer.MAX_VALUE);
    }
}
