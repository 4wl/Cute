package sb.love.module.movement;

import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import sb.love.event.MoveEvent;
import sb.love.module.Module;
import sb.love.setting.Setting;
import sb.love.util.Wrapper;

public class LongJump extends Module {
    public LongJump() {
        super("LongJump", Category.MOVEMENT);
    }

    private Setting<Boolean> packet = register("Packet", false);

    private Setting<Integer> speed = register("Speed", 30, 1, 100);



    private boolean jumped = false;
    private boolean boostable = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {

        if (nullCheck()) return;

        if (jumped)
        {
            if (Wrapper.getPlayer().onGround || Wrapper.getPlayer().capabilities.isFlying)
            {
                jumped = false;

                Wrapper.getPlayer().motionX = 0.0;
                Wrapper.getPlayer().motionZ = 0.0;

                if (packet.getValue())
                {
                    Wrapper.getPlayer().connection.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
                    Wrapper.getPlayer().connection.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX + Wrapper.getPlayer().motionX, 0.0, Wrapper.getPlayer().posZ + Wrapper.getPlayer().motionZ, Wrapper.getPlayer().onGround));
                }

                return;
            }

            if (!(Wrapper.getPlayer().movementInput.moveForward != 0f ||Wrapper.getPlayer().movementInput.moveStrafe != 0f)) return;
            double yaw = getDirection();
            Wrapper.getPlayer().motionX = -Math.sin(yaw) * (((float) Math.sqrt(Wrapper.getPlayer().motionX * Wrapper.getPlayer().motionX + Wrapper.getPlayer().motionZ * Wrapper.getPlayer().motionZ)) * (boostable ? (speed.getValue() / 10f) : 1f));
            Wrapper.getPlayer().motionZ = Math.cos(yaw) * (((float) Math.sqrt(Wrapper.getPlayer().motionX * Wrapper.getPlayer().motionX + Wrapper.getPlayer().motionZ * Wrapper.getPlayer().motionZ)) * (boostable ? (speed.getValue() / 10f) : 1f));

            boostable = false;
        }
    }



    @SubscribeEvent
    public void onMove(MoveEvent event)
    {

        if (!(Wrapper.getPlayer().movementInput.moveForward != 0f || Wrapper.getPlayer().movementInput.moveStrafe != 0f) && jumped)
        {
            Wrapper.getPlayer().motionX = 0.0;
            Wrapper.getPlayer().motionZ = 0.0;
            event.setX(0);
            event.setY(0);
        }
    }

    @SubscribeEvent
    public void onJump(LivingEvent.LivingJumpEvent event)
    {
        if ((Wrapper.getPlayer() != null && Wrapper.getWorld() != null) && event.getEntity() == Wrapper.getPlayer() && (Wrapper.getPlayer().movementInput.moveForward != 0f || Wrapper.getPlayer().movementInput.moveStrafe != 0f))
        {
            jumped = true;
            boostable = true;
        }
    }

    private double getDirection()
    {
        float rotationYaw = Wrapper.getPlayer().rotationYaw;

        if (Wrapper.getPlayer().moveForward < 0f) rotationYaw += 180f;

        float forward = 1f;

        if (Wrapper.getPlayer().moveForward < 0f) forward = -0.5f;
        else if (Wrapper.getPlayer().moveForward > 0f) forward = 0.5f;

        if (Wrapper.getPlayer().moveStrafing > 0f) rotationYaw -= 90f * forward;
        if (Wrapper.getPlayer().moveStrafing < 0f) rotationYaw += 90f * forward;

        return Math.toRadians(rotationYaw);
    }

    private boolean nullCheck() {
        return false;
    }

}
