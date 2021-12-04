package sb.love.module.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import sb.love.event.MoveEvent;
import sb.love.event.WalkEvent;
import sb.love.module.Module;
import sb.love.setting.Setting;
import sb.love.util.PlayerUtil;
import sb.love.util.Wrapper;

public class Speed extends Module {
    public Speed() {
        super("Speed", Category.MOVEMENT);
    }

    private Setting<Integer> speed = register("Speed", 9, 1, 100);
    private Setting<Boolean> strafe = register("Strafe", true);

    private int currentStage;
    private double currentSpeed;
    private double distance;
    private int cooldown;
    private int jumps;

    public void onEnable()
    {
        currentSpeed = PlayerUtil.vanillaSpeed();

        if (!Wrapper.getPlayer().onGround) currentStage = 3;
    }

    @Override
    public void onDisable()
    {
        currentSpeed = 0.0;
        currentStage = 2;

    }



    public static float getDirection() {
        final Minecraft mc = Minecraft.getMinecraft();
        float var1 = mc.player.rotationYaw;

        if(mc.player.moveForward < 0.0f) var1 += 180.0f;
        float forward = 1.0f;

        if(mc.player.moveForward < 0.0f) forward = -0.5f;
        else if(mc.player.moveForward > 0.0f) forward = 0.5f;

        if(mc.player.moveStrafing > 0.0f) var1 -= 90.f * forward;

        if(mc.player.moveStrafing < 0.0f) var1 += 90.0f * forward;

        var1 *= 0.017453292f;
        return var1;
    }

    private double getDefaultSpeed() {
        final Minecraft mc = Minecraft.getMinecraft();
        double defaultSpeed = 0.2873D;

        if (mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            defaultSpeed *= (1.0D + 0.2D * (amplifier + 1));
        }

        if (mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            final int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            defaultSpeed /= (1.0D + 0.2D * (amplifier + 1));
        }

        return defaultSpeed;
    }


    @SubscribeEvent
    public void onUpdateWalkingPlayer(WalkEvent event)
    {
        distance = Math.sqrt((Wrapper.getPlayer().posX - Wrapper.getPlayer().prevPosX) * (Wrapper.getPlayer().posX - Wrapper.getPlayer().prevPosX) + (Wrapper.getPlayer().posZ - Wrapper.getPlayer().prevPosZ) * (Wrapper.getPlayer().posZ - Wrapper.getPlayer().prevPosZ));
    }

    @SubscribeEvent
    public void onMove(MoveEvent event) {
        if (strafe.getValue()){
            float forward = Wrapper.getPlayer().movementInput.moveForward;
            float strafe = Wrapper.getPlayer().movementInput.moveStrafe;
            float yaw = Wrapper.getPlayer().rotationYaw;

            if (currentStage == 1 && PlayerUtil.isMoving()) {
                currentStage = 2;
                currentSpeed = 1.18f * PlayerUtil.vanillaSpeed() - 0.01;
            } else if (currentStage == 2) {
                currentStage = 3;

                if (PlayerUtil.isMoving()) {
                    event.setY(Wrapper.getPlayer().motionY = 0.4);
                    if (cooldown > 0) --cooldown;
                    currentSpeed *= speed.getValue() / 5f;
                }
            } else if (currentStage == 3) {
                currentStage = 4;
                currentSpeed = distance - (0.66 * (distance - PlayerUtil.vanillaSpeed()));
            } else {
                if (Wrapper.getWorld().getCollisionBoxes(Wrapper.getPlayer(), Wrapper.getPlayer().getEntityBoundingBox().offset(0.0, Wrapper.getPlayer().motionY, 0.0)).size() > 0 || Wrapper.getPlayer().collidedVertically)
                    currentStage = 1;
                currentSpeed = distance - distance / 159.0;
            }

            currentSpeed = Math.max(currentSpeed, PlayerUtil.vanillaSpeed());

            if (forward == 0.0f && strafe == 0.0f) {
                event.setX(0.0);
                event.setZ(0.0);

                currentSpeed = 0.0;
            } else if (forward != 0.0f) {
                if (strafe >= 1.0f) {
                    yaw += ((forward > 0.0f) ? -45.0f : 45.0f);
                    strafe = 0.0f;
                } else {
                    if (strafe <= -1.0f) {
                        yaw += ((forward > 0.0f) ? 45.0f : -45.0f);
                        strafe = 0.0f;
                    }
                }
                if (forward > 0.0f) forward = 1.0f;
                else if (forward < 0.0f) forward = -1.0f;
            }

            double motionX = Math.cos(Math.toRadians(yaw + 90.0f));
            double motionZ = Math.sin(Math.toRadians(yaw + 90.0f));

            if (cooldown == 0) {
                event.setX(forward * currentSpeed * motionX + strafe * currentSpeed * motionZ);
                event.setZ(forward * currentSpeed * motionZ - strafe * currentSpeed * motionX);
            }

            if (forward == 0.0f && strafe == 0.0f) {
                event.setX(0.0);
                event.setZ(0.0);
            }
        }


    }

    private boolean nullCheck() {
        return false;
    }

}
