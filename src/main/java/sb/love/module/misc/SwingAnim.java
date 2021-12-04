package sb.love.module.misc;

import sb.love.module.Module;
import sb.love.util.Wrapper;

public class SwingAnim extends Module {
    public SwingAnim() {
        super("SwingAnim", Category.MISC);
    }

    @Override
    public void onUpdate() {
        if(Wrapper.getPlayer() == null) return;

        if(Wrapper.mc.entityRenderer.itemRenderer.equippedProgressMainHand < 1)
            Wrapper.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1;

        if(Wrapper.mc.entityRenderer.itemRenderer.itemStackMainHand != Wrapper.getPlayer().getHeldItemMainhand())
            Wrapper.mc.entityRenderer.itemRenderer.itemStackMainHand = Wrapper.getPlayer().getHeldItemMainhand();
    }
}
