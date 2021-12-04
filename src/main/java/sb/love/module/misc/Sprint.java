package sb.love.module.misc;

import sb.love.module.Module;
import sb.love.util.Wrapper;
import sb.love.setting.Setting;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.MISC);
    }

    @SuppressWarnings("unchecked")
    private Setting<Boolean> onlyForward = register("OnlyForward", false);

    public void onUpdate(){
        if(Wrapper.getPlayer() == null) return;
        if(onlyForward.getValue()){
            if(Wrapper.getPlayer().moveForward > 0) Wrapper.getPlayer().setSprinting(true);
        } else {
            if(Wrapper.getPlayer().moveForward != 0) Wrapper.getPlayer().setSprinting(true);
        }
    }
}
