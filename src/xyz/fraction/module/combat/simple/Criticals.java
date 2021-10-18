package xyz.fraction.module.combat.simple;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class Criticals extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla", "NCP", "None"});

    private final IntSetting delay = new IntSetting(this, "Delay", 50, 1000, 500);

    long last = System.currentTimeMillis();

    @Override
    public void onUpdate() {
        setDisplayName(getName() + "&7 " + mode.get());
    }

    @Override
    public void onSend(PacketEvent e) {
        if (e.getPacket() instanceof C02PacketUseEntity) {
            e.setCancelled(true);

            long now = System.currentTimeMillis();

            if (now - last > delay.get()) {
                last = System.currentTimeMillis();

                switch (mode.get()) {
                    case "Vanilla":
                        mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.5, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueueSilent(e.getPacket());
                        mc.thePlayer.onCriticalHit(((C02PacketUseEntity) e.getPacket()).getEntityFromWorld(mc.theWorld));
                        return;
                    case "NCP":
                        if (!mc.thePlayer.onGround)
                            break;
                        mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.125, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueueSilent(e.getPacket());
                        mc.thePlayer.onCriticalHit(((C02PacketUseEntity) e.getPacket()).getEntityFromWorld(mc.theWorld));
                        return;
                }
            }

            if (mode.get().equals("None"))
                mc.thePlayer.onCriticalHit(((C02PacketUseEntity) e.getPacket()).getEntityFromWorld(mc.theWorld));

            mc.thePlayer.sendQueue.addToSendQueueSilent(e.getPacket());
        }
    }
}
