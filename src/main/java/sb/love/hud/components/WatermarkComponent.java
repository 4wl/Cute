package sb.love.hud.components;

import sb.love.Cute;
import sb.love.hud.HudComponent;
import sb.love.module.gui.Watermark;
import sb.love.util.Wrapper;

public class WatermarkComponent extends HudComponent<Watermark> {
    public WatermarkComponent() {
        super("Watermark", 2, 2, Watermark.INSTANCE);
    }

    @Override
    public void render() {
        super.render();
        Wrapper.getFontRenderer().drawStringWithShadow(Cute.getInstance().toString(), x, y, Cute.getInstance().clientSettings.getColor());
        width = Wrapper.getFontRenderer().getStringWidth(Cute.getInstance().toString());
    }
}
