package xyz.fraction.module.combat.complex.aura;

import org.lwjgl.input.Mouse;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.combat.complex.aura.mode.AuraNCP;
import xyz.fraction.module.combat.complex.aura.mode.AuraVanilla;
import xyz.fraction.module.combat.complex.aura.mode.AuraVanilla2;
import xyz.fraction.setting.impl.ModeSetting;

public class Aura extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla", "Vanilla2", "NCP"});

    AuraBase vanilla = new AuraVanilla(), vanilla2 = new AuraVanilla2(), ncp = new AuraNCP();

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindUse.setPressed(Mouse.isButtonDown(1));
    }

    @Override
    public void onRender() {

    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + "&7 " + mode.get());

        getAura().onPre(e);
    }

    @Override
    public void onPost() {
        getAura().onPost();
    }

    @Override
    public void onSend(PacketEvent e) {
        getAura().onSend(e);
    }

    public AuraBase getAura() {
        switch (mode.get()) {
            case "Vanilla":
                return vanilla;
            case "Vanilla2":
                return vanilla2;
            case "NCP":
                return ncp;
            default:
                send("invalid aura mode : " + mode.get());
                return vanilla;
        }
    }
}
