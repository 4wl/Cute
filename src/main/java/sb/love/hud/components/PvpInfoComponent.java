package sb.love.hud.components;

import sb.love.Cute;
import sb.love.hud.HudComponent;
import sb.love.module.gui.PvpInfo;
import sb.love.util.Wrapper;

public class PvpInfoComponent extends HudComponent<PvpInfo> {
    public PvpInfoComponent() {
        super("PvpInfo", 2, 200, PvpInfo.INSTANCE);
    }

    @Override
    public void render() {
        super.render();
        String s = Cute.getInstance().moduleManager.getEnabledColor("AutoCrystal") + "CA  " + Cute.getInstance().moduleManager.getEnabledColor("Surround") + "SU  " + Cute.getInstance().moduleManager.getEnabledColor("AutoTrap") + "AT";
        Wrapper.getFontRenderer().drawStringWithShadow(s, x, y, -1);
        width = Wrapper.getFontRenderer().getStringWidth(s);
    }
}
