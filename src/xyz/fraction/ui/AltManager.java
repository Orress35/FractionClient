package xyz.fraction.ui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class AltManager extends GuiScreen {
    private GuiTextField input;

    @Override
    public void initGui() {
        int var3 = height / 2 - 20;

        buttonList.add(new GuiButton(0, width / 2 + 36, var3 - 24, "login"));
        buttonList.add(new GuiButton(1, width / 2 + 36, var3, "add"));

        input = new GuiTextField(var3, mc.fontRendererObj, 2 + width / 2 + 36, var3 - 52, 196, 20);
        input.setFocused(true);

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        drawDefaultBackground();

        input.drawTextBox();

        if (input.getText().isEmpty()) {
            drawString(this.mc.fontRendererObj, "user:pass", 5 + width / 2 + 36, height / 2 - 52 + 6 - 20, -7829368);
        }

        super.drawScreen(x2, y2, z2);
    }

    @Override
    protected void keyTyped(char character, int key) throws IOException {
        super.keyTyped(character, key);

        if (character == '\r') {
            actionPerformed(this.buttonList.get(0));
        }

        input.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) throws IOException {
        super.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        input.setFocused(true);
        input.updateCursorCounter();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {

        } else {

        }
    }
}
