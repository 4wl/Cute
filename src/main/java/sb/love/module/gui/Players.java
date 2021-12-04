package sb.love.module.gui;

import sb.love.module.Module;
import sb.love.setting.Setting;

public class Players extends Module {
    public Players() {
        super("Players", Category.GUI);
        INSTANCE = this;
    }

    public static Players INSTANCE;

    public Setting<Align> align = register("Align", Align.RIGHT);

    public enum Align{
        LEFT, RIGHT
    }
}
