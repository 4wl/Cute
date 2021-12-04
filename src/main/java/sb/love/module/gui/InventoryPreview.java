package sb.love.module.gui;

import sb.love.module.Module;
import sb.love.setting.Setting;

public class InventoryPreview extends Module {
    public InventoryPreview() {
        super("Inventory", Category.GUI);
        INSTANCE = this;
    }

    public static InventoryPreview INSTANCE;

    public Setting<Background> background = register("Background", Background.TRANS);


    public enum Background{
        NONE, CLEAR, NORMAL, TRANS
    }
}
