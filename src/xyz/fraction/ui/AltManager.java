package xyz.fraction.ui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;
import xyz.fraction.Fraction;
import xyz.fraction.util.ColorUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AltManager extends GuiScreen {
    private String status = "";
    private GuiTextField input;
    private final List<Alt> alts = new ArrayList<>();

    @Override
    public void initGui() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(Fraction.CLIENT_ALTS));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0)
                    alts.add(new Alt(line));
            }

            br.close();
        } catch (Exception ignored) { }

        buttonList.add(new GuiButton(0, width - 230, 46, "login"));
        buttonList.add(new GuiButton(1, width - 230, 72, "add"));

        input = new GuiTextField(0, mc.fontRendererObj, width - 228, 20, 196, 20);
        input.setFocused(true);

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        drawDefaultBackground();

        drawCenteredString(mc.fontRendererObj, status, width / 2, height - 30, ColorUtil.getColor(255, 200, 200, 200));

        input.drawTextBox();

        int x = 20;
        int y = 20;
        for (Alt alt: alts) {
            drawString(mc.fontRendererObj, alt.getName(), x, y, ColorUtil.getColor(255, 200, 200, 200));
            y += 20;
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

        int x = 20;
        int y = 20;
        for (Alt alt: alts) {
            if (x2 > x && x2 < x + 100 && y2 > y && y2 < y + 20) {
                if (button == 0) {
                    status = "logged in as " + alt.getName();
                    mc.session = alt.login();
                } else {
                    alts.remove(alt);
                    break;
                }
            }

            y += 20;
        }
    }

    @Override
    public void onGuiClosed() {
        try {
            PrintWriter pw = new PrintWriter(Fraction.CLIENT_ALTS);

            for (Alt alt: alts)
                pw.println(alt.toString());

            pw.close();
        } catch (Exception ignored) { }

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
            if (input.getText().length() > 0) {
                Alt alt = new Alt(input.getText());
                Session session = alt.login();
                if (session != null) {
                    status = "logged in as " + alt.getName();
                    mc.session = session;
                } else {
                    status = "couldn't log into " + alt.getUser();
                }

                input.setText("");
            }
        } else {
            if (input.getText().length() > 0) {
                Alt alt = new Alt(input.getText());
                Session session = alt.login();
                if (session != null) {
                    status = "logged in as " + alt.getName();
                    mc.session = session;
                    alts.add(alt);
                } else {
                    status = "couldn't log into " + alt.getUser();
                }

                input.setText("");
            }
        }
    }
}
