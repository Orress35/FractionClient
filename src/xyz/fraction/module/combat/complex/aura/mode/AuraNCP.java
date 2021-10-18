package xyz.fraction.module.combat.complex.aura.mode;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.combat.complex.aura.AuraBase;
import xyz.fraction.util.SilentRotations;

public class AuraNCP extends AuraBase {
    private EntityLivingBase target;

    boolean autoBlock = false;

    @Override
    public void onPre(PreMotionEvent e) {
        mc.gameSettings.keyBindUse.setPressed(Mouse.isButtonDown(1));

        target = getClosest();

        if (target == null)
            return;

        SilentRotations.set(getRotations(target)[0], SilentRotations.getPitch());
    }

    @Override
    public void onPost() {
        autoBlock = false;
        if (target == null)
            return;

        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            if (!mc.gameSettings.keyBindUse.isKeyDown())
                mc.gameSettings.keyBindUse.setPressed(true);
            autoBlock = true;
        }

        if (mc.thePlayer.getDistanceToEntity(target) < 4.25 && Math.random() > 0.2 && mc.thePlayer.ticksExisted % 2 == 0) {
            mc.thePlayer.swingItem();
            mc.playerController.attackEntity(mc.thePlayer, target);
        }
    }

    @Override
    public void onSend(PacketEvent e) {
        if (e.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            if (autoBlock) {
                C08PacketPlayerBlockPlacement blockPlace = (C08PacketPlayerBlockPlacement) e.getPacket();
                blockPlace.setFacingX(0);
                blockPlace.setFacingY(0);
                blockPlace.setFacingZ(0);
                blockPlace.setPlacedBlockDirection(255);
                blockPlace.setPosition(new BlockPos(-1, -1, -1));
            }
        }
    }
}
