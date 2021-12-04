package sb.love.gui;

import sb.love.Cute;
import sb.love.hud.HudComponent;
import sb.love.module.Module;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends GuiScreen {

    private List<Panel> panels;

    public ClickGUI(){
        panels = new ArrayList<>();
        int x = 10;
        for(Module.Category c : Module.Category.values()){
            Panel panel = new Panel(c, x, 15);
            panels.add(panel);
            x += panel.getWidth() + 10;
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for(Panel p : panels){
            p.draw(mouseX, mouseY);
        }

        for(HudComponent c : Cute.getInstance().hudComponentManager.getComponents()){
            c.renderInGui(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for(Panel p : panels){
            p.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(Panel p : panels){
            p.mouseClicked(mouseX, mouseY, mouseButton);
        }

        for(HudComponent c : Cute.getInstance().hudComponentManager.getComponents()){
            c.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for(Panel p : panels){
            p.mouseReleased(mouseX, mouseY, state);
        }

        for(HudComponent c : Cute.getInstance().hudComponentManager.getComponents()){
            c.mouseReleased(mouseX, mouseY, state);
        }

        super.mouseReleased(mouseX, mouseY, state);
    }


    @Override
    public void onGuiClosed() {
        for(HudComponent c : Cute.getInstance().hudComponentManager.getComponents()){
            c.onGuiClosed();
        }
        super.onGuiClosed();
    }


    @Override
    public void drawDefaultBackground() {
        super.drawDefaultBackground();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
