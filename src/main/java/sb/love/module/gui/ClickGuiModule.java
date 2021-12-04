package sb.love.module.gui;

import sb.love.Cute;
import sb.love.module.Module;
import sb.love.util.Wrapper;
import org.lwjgl.input.Keyboard;

public class ClickGuiModule extends Module {
    public ClickGuiModule() {
        super("ClickGUI", Category.GUI);
        setBind(Keyboard.KEY_P);
    }

    @Override
    public void onEnable(){
        if(Wrapper.mc.currentScreen != null)
            Wrapper.mc.displayGuiScreen(null);

        Wrapper.mc.displayGuiScreen(Cute.getInstance().clickGUI);

        disable();
    }
}
