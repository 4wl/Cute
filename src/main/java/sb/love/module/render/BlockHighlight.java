package sb.love.module.render;

import sb.love.Cute;
import sb.love.module.Module;
import sb.love.util.RenderUtils;
import sb.love.util.Wrapper;
import sb.love.setting.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class BlockHighlight extends Module {
    public BlockHighlight() {
        super("BlockHighlight", Category.RENDER);
    }

    private Setting<Integer> alpha = register("Alpha", 255, 1, 255);
    private Setting<Integer> width = register("Width", 1, 1, 10);

    public void onRender3D(){
        if(Wrapper.getPlayer() == null || Wrapper.getWorld() == null || Wrapper.mc.objectMouseOver == null || Wrapper.mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) return;
        BlockPos blockpos = Wrapper.mc.objectMouseOver.getBlockPos();
        IBlockState iblockstate = Wrapper.getWorld().getBlockState(blockpos);

        if (iblockstate.getMaterial() != Material.AIR) {
            RenderUtils.INSTANCE.drawBoundingBox(iblockstate.getSelectedBoundingBox(Wrapper.getWorld(), blockpos), Cute.getInstance().clientSettings.getColor(alpha.getValue()), width.getValue().floatValue());
        }
    }
}
