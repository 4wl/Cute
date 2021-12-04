package sb.love.module.misc;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import sb.love.module.Module;
import sb.love.setting.Setting;
import sb.love.util.RenderUtil;
import sb.love.util.Wrapper;

import java.util.Objects;

public class PacketMine extends Module {

    public PacketMine() {
        super("PacketMine", Category.MISC);
    }

    private Setting<Boolean> render = register("Render", true);

    private int timer = -1;
    private BlockPos renderBlock = null;

    @SubscribeEvent
    public void onBlockClick(PlayerInteractEvent.LeftClickBlock event)
    {
        if (Wrapper.getWorld().getBlockState(event.getPos()).getBlock().getBlockHardness(Wrapper.getWorld().getBlockState(event.getPos()), Wrapper.getWorld(), event.getPos()) != -1)
        {
            Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
            Wrapper.getPlayer().connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), Objects.requireNonNull(event.getFace())));
            Wrapper.getPlayer().connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFace()));
            if (renderBlock == null && render.getValue()) renderBlock = event.getPos();
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event)
    {
        if (renderBlock != null && timer > 0)
        {
            RenderUtil.drawBoxFromBlockpos(renderBlock, 0.3f, 0.3f, 0.3f, 0.5f);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        if (renderBlock != null && Wrapper.getWorld().getBlockState(renderBlock).getBlock() == Blocks.AIR) renderBlock = null;
        if (renderBlock != null && timer == -1) timer = 130;
        if (timer > 0) timer--;
        if (timer == 0)
        {
            timer = -1;
            renderBlock = null;
        }
    }



}
