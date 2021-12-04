package sb.love.module.gui;

import sb.love.module.Module;

public class Watermark extends Module {
    public Watermark() {
        super("Watermark", Category.GUI);
        INSTANCE = this;
    }

    public static Watermark INSTANCE;
}
