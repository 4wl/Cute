package sb.love.module.misc;

import sb.love.module.Module;
import sb.love.util.Wrapper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class FastUse extends Module {
    public FastUse() {
        super("FastUse", Category.MISC);
    }

    @Override
    public void onUpdate() {
        if(Wrapper.getPlayer() == null) return;
        Wrapper.mc.playerController.blockHitDelay = 0;
        if (Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemExpBottle || Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemEndCrystal || offhand()) Wrapper.mc.rightClickDelayTimer = 0;
    }

    private boolean offhand() {
        boolean item = Wrapper.getPlayer().getHeldItemOffhand().getItem() instanceof ItemEndCrystal || Wrapper.getPlayer().getHeldItemOffhand().getItem() instanceof ItemExpBottle;
        boolean block = Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemBlock;
        return item && !block;
    }
}
