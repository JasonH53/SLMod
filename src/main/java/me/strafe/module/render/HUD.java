package me.strafe.module.render;

import me.strafe.StrafeLegitMod;
import me.strafe.module.Category;
import me.strafe.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUD extends Module {

    public HUD() {
        super("HUD", "Shows thing on screen", Category.RENDER);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent egoe) {
        if (!egoe.type.equals(egoe.type.TEXT.CROSSHAIRS)) {
            return;
        }

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int y=2;
        for (Module modules: StrafeLegitMod.instance.moduleManager.getModuleList()) {
            if (!modules.getName().equalsIgnoreCase("HUD") && modules.isToggled() && modules.visible) {
                FontRenderer fr = mc.fontRendererObj;
                fr.drawString(modules.getName(),sr.getScaledWidth()- fr.getStringWidth(modules.getName()) - 1, y, 0xFFFFFF);
                y += fr.FONT_HEIGHT;
            }
        }
    }

}
