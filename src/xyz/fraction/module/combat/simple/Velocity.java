package xyz.fraction.module.combat.simple;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.module.Module;
import xyz.fraction.setting.impl.ModeSetting;

public class Velocity extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Cancel"});

    @Override
    public void onReceive(PacketEvent e) {
        setDisplayName(getName() + "&7 " + mode.get());
        if (mode.get().equals("Cancel")) {
            if (e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion)
                e.setCancelled(true);
        }
    }
}
