package sb.love.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class PlayerUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static double vanillaSpeed()
    {
        double baseSpeed = 0.272;
        if (Minecraft.getMinecraft().player.isPotionActive(MobEffects.SPEED))
        {
            final int amplifier = Objects.requireNonNull(Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * amplifier;
        }
        return baseSpeed;
    }

    public static boolean isMoving()
    {
        return Minecraft.getMinecraft().player.moveForward != 0.0 || Minecraft.getMinecraft().player.moveStrafing != 0.0;
    }

    public static int getSlot(Item item)
    {
        for (int i = 0; i < 9; i++)
        {
            Item item1 = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();

            if (item.equals(item1))
            {
                return i;
            }
        }
        return -1;
    }

    public static int getSlot(Block block)
    {
        for (int i = 0; i < 9; i++)
        {
            Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();

            if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().equals(block))
            {
                return i;
            }
        }
        return -1;
    }



    public static boolean isIntercepted(BlockPos pos)
    {
        for (Entity entity : mc.world.loadedEntityList)
        {
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
        }

        return false;
    }

    public static double getDirection()
    {
        float rotationYaw = mc.player.rotationYaw;

        if (mc.player.moveForward < 0f) rotationYaw += 180f;

        float forward = 1f;

        if (mc.player.moveForward < 0f) forward = -0.5f;
        else if (mc.player.moveForward > 0f) forward = 0.5f;

        if (mc.player.moveStrafing > 0f) rotationYaw -= 90f * forward;
        if (mc.player.moveStrafing < 0f) rotationYaw += 90f * forward;

        return Math.toRadians(rotationYaw);
    }
}
