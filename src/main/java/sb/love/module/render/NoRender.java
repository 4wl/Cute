package sb.love.module.render;

import sb.love.module.Module;
import sb.love.setting.Setting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender extends Module {
    public NoRender() {
        super("NoRender", Category.RENDER);
    }

    private Setting<Boolean> armorBar = register("ArmorBar", true);

    @SubscribeEvent
    public void preRenderGameOverlay(RenderGameOverlayEvent.Pre event){
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.ARMOR) && armorBar.getValue()){
            event.setCanceled(true);
        }
    }
}
