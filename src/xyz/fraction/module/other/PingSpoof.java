package xyz.fraction.module.other;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.IntSetting;

import java.util.HashMap;
import java.util.Map;

@ModuleInfo()
public class PingSpoof extends Module {
    private final IntSetting delay = new IntSetting(this, "Delay", 100, 1000, 250);

    private final Map<C00PacketKeepAlive, Long> keepAlives = new HashMap<>();
    private final Map<C0FPacketConfirmTransaction, Long> transactions = new HashMap<>();

    @Override
    public void onUpdate() {
        if (!keepAlives.isEmpty()) {
            for (C00PacketKeepAlive c00 : keepAlives.keySet()) {
                if (System.currentTimeMillis() - keepAlives.get(c00) > delay.get()) {
                    mc.thePlayer.sendQueue.addToSendQueueSilent(c00);
                    keepAlives.remove(c00);
                    break;
                }
            }
        }

        if (!transactions.isEmpty()) {
            for (C0FPacketConfirmTransaction c0f : transactions.keySet()) {
                if (System.currentTimeMillis() - transactions.get(c0f) > delay.get()) {
                    mc.thePlayer.sendQueue.addToSendQueueSilent(c0f);
                    transactions.remove(c0f);
                    break;
                }
            }
        }
    }

    @Override
    public void onSend(PacketEvent e) {
        if (e.getPacket() instanceof C00PacketKeepAlive) {
            e.setCancelled(true);
            keepAlives.put((C00PacketKeepAlive) e.getPacket(), System.currentTimeMillis());
        }

        if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
            e.setCancelled(true);
            transactions.put((C0FPacketConfirmTransaction) e.getPacket(), System.currentTimeMillis());
        }
    }
}
