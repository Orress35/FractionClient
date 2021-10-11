package xyz.fraction.ui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import xyz.fraction.Fraction;
import xyz.fraction.module.Category;
import xyz.fraction.module.Module;
import xyz.fraction.setting.Setting;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.ColorUtil;
import xyz.fraction.util.font.FontRenderer;
import xyz.fraction.util.font.Fonts;

import java.io.IOException;
import java.util.List;

public class ClickGUI extends GuiScreen {
    int panelX = 0;
    Module open = null;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        panelX = -80;

        for (Category category: Category.values())
            renderPanel(category);

        if (open != null) {
            renderSettings(open);
        }
    }

    public void renderPanel(Category category) {
        panelX += 120;

        List<Module> modules = Fraction.INSTANCE.getModuleManager().getModulesByCategory(category);

        FontRenderer fr = Fonts.fractionNormal;

        drawRect(panelX - 10, 20, panelX + 80, 40, ColorUtil.getColor(255, 40, 40, 40));
        fr.drawCenteredStringWithShadow(category.name(), (panelX - 10 + panelX + 80) / 2F, 25, ColorUtil.getColor(255, 200, 200, 200));

        int y = 40;
        for (Module module: modules) {
            drawRect(panelX - 8, y, panelX + 78, y + 20, ColorUtil.getColor(255, 50, 50, 50));
            fr.drawCenteredStringWithShadow(module.getName(), (panelX - 8 + panelX + 78) / 2F, y + 5, module.isEnabled() ? ColorUtil.getColor(255, 120, 200, 120) : ColorUtil.getColor(255, 200, 200, 200));

            if (!module.getSettings().isEmpty())
                fr.drawStringWithShadow("(...)", panelX + 60, y + 5, ColorUtil.getColor(255, 200, 200, 200));

            y += 20;
        }
    }

    public void renderSettings(Module module) {
        FontRenderer fr = Fonts.fractionBig;
        ScaledResolution sr = new ScaledResolution(mc);

        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        int i = width / 3, j = height / 10;

        drawRect(i, j, width - i, height - j, ColorUtil.getColor(255, 45, 45, 45));
        fr.drawCenteredStringWithShadow(module.getName(), width / 2F, height / 10F + 3, ColorUtil.getColor(255, 200, 200, 200));

        int x = i + 8;
        int y = j + 3;
        for (Setting setting: module.getSettings()) {
            y += 20;
            if (setting instanceof BooleanSetting) {
                drawRect(x, y, x + 16, y + 16, ColorUtil.getColor(255, 200, 200, 200));
                drawRect(x + 1, y + 1, x + 15, y + 15, ColorUtil.getColor(255, 45, 45, 45));

                if (((BooleanSetting) setting).get())
                    drawRect(x + 2, y + 2, x + 14, y + 14, ColorUtil.getColor(255, 120, 200, 120));

                fr.drawStringWithShadow(setting.getName(), x + 20, y, ColorUtil.getColor(255, 200, 200, 200));
            } else if (setting instanceof DoubleSetting) {
                double range = ((DoubleSetting) setting).getMax() - ((DoubleSetting) setting).getMin();
                int percentage = (int) Math.round((((DoubleSetting) setting).get() - ((DoubleSetting) setting).getMin()) / range * 100);
                if (percentage == 0)
                    percentage = 1;
                fr.drawStringWithShadow(setting.getName() + ": ", x, y, ColorUtil.getColor(255, 200, 200, 200));
                fr.drawStringWithShadow(String.format("%.2f", ((DoubleSetting) setting).get()), x + fr.getStringWidth(setting.getName() + ": "), y, ColorUtil.getColor(255, 120, 200, 120));
                drawRect(x, y + 15, x + (int) Math.round((width - x - i - 8) * percentage / 100D), y + 16, ColorUtil.getColor(255, 120, 200, 120));
            } else if (setting instanceof IntSetting) {
                double range = ((IntSetting) setting).getMax() - ((IntSetting) setting).getMin();
                int percentage = (int) Math.round((((IntSetting) setting).get() - ((IntSetting) setting).getMin()) / range * 100);
                if (percentage == 0)
                    percentage = 1;
                fr.drawStringWithShadow(setting.getName() + ": ", x, y, ColorUtil.getColor(255, 200, 200, 200));
                fr.drawStringWithShadow(String.format("%d", ((IntSetting) setting).get()), x + fr.getStringWidth(setting.getName() + ": "), y, ColorUtil.getColor(255, 120, 200, 120));
                drawRect(x, y + 15, x + (int) Math.round((width - x - i - 8) * percentage / 100D), y + 16, ColorUtil.getColor(255, 120, 200, 120));
            } else if (setting instanceof ModeSetting) {
                fr.drawStringWithShadow(setting.getName() + ": ", x, y, ColorUtil.getColor(255, 200, 200, 200));
                fr.drawStringWithShadow(((ModeSetting) setting).get(), x + fr.getStringWidth(setting.getName() + ": "), y, ColorUtil.getColor(255, 120, 200, 120));
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            if (open != null) {
                open = null;
            } else {
                mc.displayGuiScreen(null);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (open == null) {
            panelX = -80;

            for (Category category : Category.values())
                clickPanel(category, mouseX, mouseY, mouseButton);
        } else {
            clickSettings(open, mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (open != null) {
            ScaledResolution sr = new ScaledResolution(mc);

            int width = sr.getScaledWidth();
            int height = sr.getScaledHeight();
            int i = width / 3, j = height / 10;

            int y = j + 3;
            for (Setting setting: open.getSettings()) {
                y += 20;

                if (mouseX <= i || mouseX >= width - i)
                    continue;

                if (mouseY <= y || mouseY >= y + 20)
                    continue;

                int minX = i + 8;
                int maxX = width - i - 8;
                int relX = (int) Math.round((double) (mouseX - minX) / (double) (maxX - minX) * 100);
                if (setting instanceof DoubleSetting) {
                    double range = ((DoubleSetting) setting).getMax() - ((DoubleSetting) setting).getMin();
                    ((DoubleSetting) setting).set(((DoubleSetting) setting).getMin() + relX / 100D * range);
                    if (((DoubleSetting) setting).get() < ((DoubleSetting) setting).getMin())
                        ((DoubleSetting) setting).set(((DoubleSetting) setting).getMin());
                    if (((DoubleSetting) setting).get() > ((DoubleSetting) setting).getMax())
                        ((DoubleSetting) setting).set(((DoubleSetting) setting).getMax());
                } else if (setting instanceof IntSetting) {
                    int range = ((IntSetting) setting).getMax() - ((IntSetting) setting).getMin();
                    ((IntSetting) setting).set((int) Math.round(((IntSetting) setting).getMin() + relX / 100D * range));
                    if (((IntSetting) setting).get() < ((IntSetting) setting).getMin())
                        ((IntSetting) setting).set(((IntSetting) setting).getMin());
                    if (((IntSetting) setting).get() > ((IntSetting) setting).getMax())
                        ((IntSetting) setting).set(((IntSetting) setting).getMax());
                }
            }
        }
    }

    public void clickPanel(Category category, int mouseX, int mouseY, int mouseButton) {
        panelX += 120;

        List<Module> modules = Fraction.INSTANCE.getModuleManager().getModulesByCategory(category);

        int y = 40;
        for (Module module: modules) {
            if (mouseX > panelX - 8 && mouseX < panelX + 78 && mouseY > y && mouseY < y + 20) {
                if (mouseButton == 0)
                    module.toggle();
                else
                    open = module;
            }

            y += 20;
        }
    }

    public void clickSettings(Module module, int mouseX, int mouseY, int mouseButton) {
        ScaledResolution sr = new ScaledResolution(mc);

        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        int i = width / 3, j = height / 10;

        int y = j + 3;
        for (Setting setting: module.getSettings()) {
            y += 20;

            if (mouseX <= i || mouseX >= width - i)
                continue;

            if (mouseY <= y || mouseY >= y + 20)
                continue;

            if (setting instanceof BooleanSetting) {
                ((BooleanSetting) setting).toggle();
            } else if (setting instanceof DoubleSetting) {

            } else if (setting instanceof IntSetting) {

            } else if (setting instanceof ModeSetting) {
                if (mouseButton == 0) {
                    ((ModeSetting) setting).inc();
                } else {
                    ((ModeSetting) setting).dec();
                }
            }
        }
    }
}
