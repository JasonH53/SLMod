package me.strafe.module.render;

import me.strafe.StrafeLegitMod;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.settings.Setting;
import me.strafe.utils.ChatUtils;
import me.strafe.utils.DiscordWebhook;
import me.strafe.utils.Utils;
import me.strafe.utils.handlers.TextRenderer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.util.ArrayList;

import static me.strafe.utils.Registers.Registers.*;

public class PlayerDisplayer extends Module {

    public boolean active;
    public boolean reset;
    public static String Watchdog;
    public static ArrayList<String> Checked;
    public static boolean Checking;
    public static String Guild;
    public static int timeout;
    public static String name2;
    public static String name3;
    public static int tick = 0;
    public static int tick2 = 0;
    public static boolean leo = false;
    public static boolean leo2 = false;
    public static boolean jerry_li = true;
    public static int jerry = 0;

    public static String[] daysuki = new String[35];

    public PlayerDisplayer() {
        super("FED Detector", "Shows Players", Category.RENDER);
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Hide Friend", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Hide Invisible", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Hide NPC", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Hide Watchdog", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Show Distance", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Show Everything", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Ear Rape", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Send to Webhook", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Send Fed to party chat", this, false));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting());
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Distance", this, 40, 0, 60, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("X Location", this, 50, 0, 200, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Y Location", this, 50, 0, 200, false));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        active = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        active = false;
    }

    public static String WatchdogName(Entity e) {
        if (e.isInvisible() && e.posY >= PlayerDisplayer.mc.thePlayer.posY + 7.0) {
            return e.getName();
        }
        return null;
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) throws IOException, InterruptedException {

        if (!active || PlayerDisplayer.mc.thePlayer == null) {
            return;
        }
        if (PlayerDisplayer.mc.currentScreen == null) {
            int a = 0;
            for (Entity entity : PlayerDisplayer.mc.theWorld.getLoadedEntityList()) {
                if (!(entity instanceof EntityPlayer) || !((double) entity.getDistanceToEntity(PlayerDisplayer.mc.thePlayer) < StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Distance").getValDouble()
                        || entity.getName().equals(PlayerDisplayer.mc.thePlayer.getName()))) {
                    continue;
                }
                if (Watchdog == null) {
                    Watchdog = PlayerDisplayer.WatchdogName(entity);
                }
                String name = entity.getName();
                String c = "";
                String f = EnumChatFormatting.RED + "" + (int) ((EntityPlayer) entity).getHealth() + " " + EnumChatFormatting.WHITE;
                String g = "";
                boolean d = true;
                boolean other = true;
                if (entity == mc.thePlayer) {
                    d = false;
                }

                if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Hide NPC").getValBoolean()) {
                    if (entity.getName().equalsIgnoreCase("taurus") || entity.getName().equalsIgnoreCase("BarbarianGuard")) {
                        d = false;
                        break;
                    }
                    if ((int) ((EntityPlayer) entity).getHealth() > 500) {
                        d = false;
                    }
                }

                if (Utils.isNPC(entity)) {
                    name = EnumChatFormatting.DARK_GRAY + name;
                    c = EnumChatFormatting.DARK_GRAY + " (Bot)";
                    other = false;
                    if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Hide NPC").getValBoolean()) {
                        d = false;
                    }
                }

                if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Hide Friend").getValBoolean()) {
                    for (int i = 0; i <= FriendsDatabase.length - 1; i++) {
                        if (entity.getName().equalsIgnoreCase(FriendsDatabase[i].getName())) {
                            d = false;
                            break;
                        }
                    }
                }

                if (entity.isInvisible()) {
                    name = EnumChatFormatting.GRAY + name;
                    g = EnumChatFormatting.GRAY + " (Invisible)";
                    if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Show Distance").getValBoolean()) {
                        d = false;
                    }
                }
                if (entity.getName().equals(Watchdog)) {
                    if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Hide Watchdog").getValBoolean()) {
                        d = false;
                    }
                }

                String b;
                if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Show Distance").getValBoolean()) {
                    b = EnumChatFormatting.GREEN + " [" + Math.round(entity.getDistanceToEntity(PlayerDisplayer.mc.thePlayer)) + "m]";
                } else {
                    b = "";
                }
                if (d || StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Show Everything").getValBoolean()) {
                    TextRenderer.drawString(f + name + c + g + b, (int) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "X Location").getValDouble(), (int) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Y Location").getValDouble() + (a += 10), 3);
                    if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Ear Rape").getValBoolean()) {
                        mc.thePlayer.playSound("random.door_open", 1F, 1);
                    }
                    if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Send to Webhook").getValBoolean()) {
                        leo2 = true;
                        name3 = entity.getName();
                    }
                        if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Send Fed to party chat").getValBoolean()) {
                            leo = true;
                            name2 = entity.getName();
                        }
                    }
                    TextRenderer.drawString(EnumChatFormatting.DARK_GREEN + "Players (" + a / 10 + "):", (int) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "X Location").getValDouble(), (int) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Y Location").getValDouble(), 3);
                }
            }
        }

        @SubscribeEvent
        public void onTick (TickEvent.ClientTickEvent event){
            if (Checking) {
                if (++timeout >= 200) {
                    timeout = 0;
                    Checking = false;
                }
            } else {
                timeout = 0;
            }

            if (leo) {
                if (tick == 200) {
                    ChatUtils.sendMessage("/pc FED DETECTED!!! USERNAME: "+ name2);
                    leo = false;
                    tick = 0;
                }
                tick++;
            }

            if (leo2) {
                if (tick2 == 200) {
                    new Thread(() -> {
                        try {
                            String url = "https://discord.com/api/webhooks/997840037242740747/htRxGdFaISSorsIO5ncS931TD4dLQBsQeE8UfwFCjnzbz91t5q2mPKasyK2O9wfxrr6m";
                            DiscordWebhook web = new DiscordWebhook(url);
                            web.setContent("<@!222635812389519361>FED DETECTED + USERNAME: `" + name3 + "`");
                            web.execute();
                        } catch (IOException k) {
                            k.printStackTrace();
                        }
                    }).start();
                    leo2 = false;
                    tick2=0;
                }
                tick2++;
                }
            }

        @SubscribeEvent
        public void onWorldChange (WorldEvent.Load event){
            StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Send Fed to party chat").setValBoolean(false);
        }

        static {
            Watchdog = null;
            Checked = new ArrayList();
            Checking = false;
            timeout = 0;
        }


    }