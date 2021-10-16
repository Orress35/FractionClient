package xyz.fraction.module.testing;

import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;

import java.util.Arrays;
import java.util.List;

@ModuleInfo()
public class PacketDuplicator extends Module {
    private final BooleanSetting debug = new BooleanSetting(this, "Debug", false);

    @Override
    public void onSend(PacketEvent e) {
        List<String> classes = Arrays.asList(
                "C0APacketAnimation", "C0BPacketEntityAction", "C0CPacketInput", "C0DPacketCloseWindow", "C02PacketUseEntity",
                "C07PacketPlayerDigging", "C08PacketPlayerBlockPlacement", "C09PacketHeldItemChange", "C12PacketUpdateSign",
                "C13PacketAbilities", "C16PacketClientStatus", "C17PacketCustomPayload"
        );

        if (classes.contains(e.getPacket().getClass().getSimpleName())) {
            if (Math.random() > 0.95) {
                mc.thePlayer.sendQueue.addToSendQueueSilent(e.getPacket());
                if (debug.get())
                    send("duplicated &a" + e.getPacket().getClass().getSimpleName());
            }
        }
    }
}
