package xyz.fraction.module.movement;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class Sneak extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla", "Packet", "Legit"});

    @Override
    public void onDisable() {
        mc.thePlayer.sendQueue.addToSendQueueSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7" + mode.get());
        if (mode.get().equals("Vanilla"))
            e.setSneaking(true);
        else if (mode.get().equals("Legit"))
            mc.gameSettings.keyBindSneak.setPressed(true);
        else
            mc.thePlayer.sendQueue.addToSendQueueSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }

    @Override
    public void onPost() {
        if (mode.get().equals("Packet"))
            mc.thePlayer.sendQueue.addToSendQueueSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
    }
}
