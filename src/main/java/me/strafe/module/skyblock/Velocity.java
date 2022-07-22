package me.strafe.module.skyblock;

import me.strafe.StrafeLegitMod;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.settings.Setting;
import me.strafe.utils.ChatUtils;
import me.strafe.utils.handlers.ScoreboardHandler;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {
    public Velocity () {
        super("Velocity", "KB modifier", Category.SKYBLOCK);
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Horizontal", this, 0, 0, 100, true));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Vertical", this, 0, 0, 100, true));
    }

    public void onEnable() {
        super.onEnable();
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        float horizontal = (float) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
        float vertical = (float) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
        if (mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
            mc.thePlayer.motionX = horizontal/100;
            mc.thePlayer.motionY = vertical/100;
            mc.thePlayer.motionZ = horizontal/100;
        }
    }
}
