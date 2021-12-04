package sb.love.module.gui;

import sb.love.module.Module;

public class PvpInfo extends Module {
    public PvpInfo() {
        super("PvpInfo", Category.GUI);
        INSTANCE = this;
    }

    public static PvpInfo INSTANCE;
}
