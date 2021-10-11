package xyz.fraction.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import xyz.fraction.Fraction;
import xyz.fraction.module.Module;
import xyz.fraction.util.font.FontRenderer;
import xyz.fraction.util.font.Fonts;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HUD extends GuiIngame {
    public HUD(Minecraft mcIn) {
        super(mcIn);
    }

    @Override
    public void renderGameOverlay(float partialTicks) {
        super.renderGameOverlay(partialTicks);

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        FontRenderer fr = Fonts.fractionNormal;
        List<Module> modules = new ArrayList<>();

        for (Module module: Fraction.INSTANCE.getModuleManager().getModules()) {
            if (module.isEnabled())
                modules.add(module);
        }

        modules.sort((m1, m2) -> fr.getStringWidth(m2.getDisplayName()) - fr.getStringWidth(m1.getDisplayName()));

        Fonts.fractionBig.drawStringWithShadow("Fraction", 2, 2, Color.HSBtoRGB((System.currentTimeMillis() - 125) % 3250 / 3250F, 0.3F, 1.0F));

        int i = 0;
        int y = 1;
        for (Module module: modules) {
            fr.drawStringWithShadow(module.getDisplayName(), sr.getScaledWidth() - fr.getStringWidth(module.getDisplayName()) - 2, y, Color.HSBtoRGB((System.currentTimeMillis() + 125 * i) % 3250 / 3250F, 0.3F, 1.0F));
            i++;
            y += 12;
        }
    }
}
