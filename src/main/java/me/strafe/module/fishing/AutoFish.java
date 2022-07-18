//package me.strafe.module.fishing;
//
//import me.strafe.StrafeLegitMod;
//import me.strafe.module.Category;
//import me.strafe.module.Module;
//import me.strafe.settings.Setting;
//import me.strafe.utils.*;
//import me.strafe.utils.math.TimeHelper;
//import net.minecraft.pathfinding.PathPoint;
//import net.minecraft.util.BlockPos;
//import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.Vec3;
//import net.minecraftforge.client.event.sound.SoundEvent;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.TickEvent;
//import org.lwjgl.input.Keyboard;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import net.minecraft.block.Block;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.entity.EntityOtherPlayerMP;
//import net.minecraft.client.gui.GuiChat;
//import net.minecraft.client.gui.ScaledResolution;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.item.EntityArmorStand;
//import net.minecraft.entity.projectile.EntityFishHook;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.Items;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.network.play.server.S2APacketParticles;
//
//public class AutoFish extends Module {
//
//    public AutoFish() {
//        super("AutoFish", "FISHING LEGIT", Category.FISHING);
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Rod Slot", this, 0, 0, 10, false));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Hyp Slot", this, 0, 0, 10, false));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Aim Speed", this, 300, 0, 1000, false));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Sneak", this, false));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Anti-AFK", this, false));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Hyp Damage", this, 1500000, 1000000, 3000000, false));
//    }
//
//
//    private static Minecraft mc = Minecraft.getMinecraft();
//    private static List<String> fishingMobs = JsonUtils.getListFromUrl("https://gist.githubusercontent.com/Apfelmus1337/da641d3805bddf800eef170cbb0068ec/raw/sea_creatures.json", "mobs");
//    private static TimeHelper warpTimer = new TimeHelper();
//    private static TimeHelper throwTimer = new TimeHelper();
//    private static TimeHelper inWaterTimer = new TimeHelper();
//    private static TimeHelper killTimer = new TimeHelper();
//    private static TimeHelper recoverTimer = new TimeHelper();
//    private static EntityArmorStand curScStand = null;
//    private static Entity curSc = null;
//    private static boolean killing = false;
//    private static int clicksLeft = 0;
//    private static boolean flash = false;
//    //        private static Location currentLocation = null;
//    private static List<PathPoint> path = null;
//    private static BlockPos oldPos = null;
//    private static double oldBobberPosY = 0.0;
//    private static boolean oldBobberInWater = false;
//    private static int ticks = 0;
//    private static Vec3 startPos = null;
//    private static Rotation startRot = null;
//    private static List<ParticleEntry> particleList = new ArrayList<ParticleEntry>();
//    private AutoFishState afs = AutoFishState.THROWING;
//    private WarpState warpState = WarpState.SETUP;
//    private AAState aaState = AAState.AWAY;
//
//    public void onEnable() {
//        this.resetVariables();
//        RotationUtils.reset();
//        if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Hyp Damage").getValDouble() <= 1000000) {
//            ChatUtils.addChatMessage("Configure your HypDamage pls ty");
////                CF4M.INSTANCE.moduleManager.toggle(this);
//            return;
//        }
//        if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Rod Slot").getValDouble() == 0) {
//            ChatUtils.addChatMessage("Configure your RodSlot pls ty");
////                CF4M.INSTANCE.moduleManager.toggle(this);
//            return;
//        }
//        if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Hyp Slot").getValDouble() != 0 && fishingMobs.isEmpty()) {
//            ChatUtils.addChatMessage("An error occured while getting Fishing Mobs, reloading...", new String[0]);
//            fishingMobs = JsonUtils.getListFromUrl("https://gist.githubusercontent.com/Apfelmus1337/da641d3805bddf800eef170cbb0068ec/raw", "mobs");
////                CF4M.INSTANCE.moduleManager.toggle(this);
//            return;
//        }
//
//        startPos = AutoFish.mc.thePlayer.getPositionVector();
//        startRot = new Rotation(AutoFish.mc.thePlayer.rotationYaw, AutoFish.mc.thePlayer.rotationPitch);
//        KeyBinding.setKeyBindState((int) AutoFish.mc.gameSettings.keyBindSneak.getKeyCode(), StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Sneak").getValBoolean());
//
//    }
//
//    public void onDisable() {
//        if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Sneak").getValBoolean()) {
//            KeyBinding.setKeyBindState((int) AutoFish.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean) false);
//        }
//        KeybindUtils.stopMovement();
//        RotationUtils.reset();
//    }
//
//    @SubscribeEvent
//    public void onInteract(PlayerInteractEvent event) {
//        throwTimer.reset();
//        inWaterTimer.reset();
//    }
//
//    @SubscribeEvent
//    public void onTick(TickEvent.ClientTickEvent event) {
//        if (AutoFish.mc.currentScreen != null && !(AutoFish.mc.currentScreen instanceof GuiChat)) {
//            return;
//        }
//
//        if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Anti-AFK").getValBoolean() && this.afs != AutoFishState.WARP_ISLAND && this.afs != AutoFishState.WARP_SPOT && this.afs != AutoFishState.NAVIGATING) {
//            KeybindUtils.stopMovement();
//            if (++ticks > 40) {
//                ticks = 0;
//                List<KeyBinding> neededPresses = VecUtils.getNeededKeyPresses(AutoFish.mc.thePlayer.getPositionVector(), startPos);
//                neededPresses.forEach(v -> KeyBinding.setKeyBindState((int) v.getKeyCode(), (boolean) true));
//                if (RotationUtils.done) {
//                    switch (this.aaState) {
//                        case AWAY: {
//                            Rotation afk = new Rotation(startRot.getYaw(), startRot.getPitch());
//                            afk.addYaw((float) (Math.random() * 4.0 - 2.0));
//                            afk.addPitch((float) (Math.random() * 4.0 - 2.0));
//                            RotationUtils.setup(afk, Long.valueOf(RandomUtil.randBetween(400, 600)));
//                            this.aaState = AAState.BACK;
//                            break;
//                        }
//                        case BACK: {
//                            RotationUtils.setup(startRot, Long.valueOf(RandomUtil.randBetween(400, 600)));
//                            this.aaState = AAState.AWAY;
//                        }
//                    }
//                }
//            }
//        }
//        if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Hyp Slot").getValDouble() != 0) {
//            if (curScStand == null || AutoFish.curScStand.isDead || curSc == null || AutoFish.curSc.isDead) {
//                curScStand = null;
//                curSc = null;
//            }
//            if (curScStand != null && SkyblockUtils.getMobHp(curScStand) <= 0) {
//                if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Rod Slot").getValDouble() > 0 && StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Rod Slot").getValDouble() <= 8) {
//                    AutoFish.mc.thePlayer.inventory.currentItem = (int) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Rod Slot").getValDouble() - 1;
//                }
//                curScStand = null;
//                curSc = null;
//            }
//            if (curSc == null && killing) {
//                RotationUtils.setup(startRot, (long) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Aim Speed").getValDouble());
//                killing = false;
//            }
//        }
//
//        particleList.removeIf(v -> System.currentTimeMillis() - v.timeAdded > 1000L);
//
//        SkyblockUtils.Location sbLoc = SkyblockUtils.getLocation();
//        if (recoverTimer.hasReached(5000L)) {
//            String recoverMsg = "";
//            switch (sbLoc) {
//                case LOBBY: {
//                    ChatUtils.addChatMessage("Detected player in Lobby, re-warping");
//                    recoverMsg = "/play skyblock";
//                    break;
//                }
//                case LIMBO: {
//                    ChatUtils.addChatMessage("Detected player in Limbo, re-warping");
//                    recoverMsg = "/l";
//                }
//            }
//            if (!recoverMsg.equals("")) {
//                this.resetVariables();
//                RotationUtils.reset();
//                AutoFish.mc.thePlayer.sendChatMessage(recoverMsg);
//                this.afs = AutoFishState.WARP_ISLAND;
//                this.warpState = WarpState.SETUP;
//            }
//            recoverTimer.reset();
//        }
//
//        if (!(this.afs != AutoFishState.THROWING && this.afs != AutoFishState.IN_WATER && this.afs != AutoFishState.FISH_BITE)) {
//
//            if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Sneak").getValBoolean()) {
//                KeyBinding.setKeyBindState((int) AutoFish.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean) true);
//            }
//                this.findAndSetCurrentSeaCreature();
//                if (curSc != null) {
//                    this.afs = AutoFishState.THROWING;
//                    throwTimer.reset();
//                }
//        }
//        switch (this.afs) {
//            case WARP_ISLAND: {
////                if (!warpTimer.hasReached(5000L)) break;
////                Optional<Location> loc = AutoFish.fishingJson.locations.stream().filter(v -> v.name.equals(this.fishingSpot.getCurrent())).findFirst();
////                loc.ifPresent(location -> {
////                    currentLocation = location;
////                });
////                if (currentLocation != null) {
////                    ChatUtils.send("Navigating to: " + AutoFish.currentLocation.name.split(" - ")[1], new String[0]);
////                    AutoFish.mc.thePlayer.sendChatMessage("/warp home");
////                    warpTimer.reset();
////                    this.afs = AutoFishState.WARP_SPOT;
////                    break;
////                }
////                ChatUtils.send("Couldn't determine location, very weird", new String[0]);
////                CF4M.INSTANCE.moduleManager.toggle(this);
////                break;
//            }
//            case WARP_SPOT: {
////                if (!warpTimer.hasReached(5000L)) break;
////                String warpLoc = AutoFish.currentLocation.name.split(" - ")[0];
////                AutoFish.mc.thePlayer.sendChatMessage(warpLoc);
////                warpTimer.reset();
////                path = null;
////                this.afs = AutoFishState.NAVIGATING;
////                break;
//            }
//            case NAVIGATING: {
////                if (!warpTimer.hasReached(5000L) || path != null) break;
////                path = new ArrayList<PathPoint>(AutoFish.currentLocation.path);
////                oldPos = null;
////                KeyBinding.setKeyBindState((int) AutoFish.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean) true);
////                warpTimer.reset();
////                this.warpState = WarpState.SETUP;
////                break;
//            }
//            case THROWING: {
//                if (AutoFish.mc.thePlayer.fishEntity == null && throwTimer.hasReached(this.recastDelay.getCurrent().intValue())) {
//                    if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
//                        AutoFish.mc.thePlayer.inventory.currentItem = this.rodSlot.getCurrent() - 1;
//                    }
//                    AutoFish.mc.playerController.sendUseItem(AutoFish.mc.thePlayer, AutoFish.mc.theWorld, AutoFish.mc.thePlayer.getHeldItem());
//                    throwTimer.reset();
//                    inWaterTimer.reset();
//                    flash = false;
//                    this.afs = AutoFishState.IN_WATER;
//                    break;
//                }
//                if (!throwTimer.hasReached(2500L) || AutoFish.mc.thePlayer.fishEntity == null) break;
//                this.afs = AutoFishState.FISH_BITE;
//                break;
//            }
//            case IN_WATER: {
//                ItemStack heldItem = AutoFish.mc.thePlayer.getHeldItem();
//                if (heldItem != null && heldItem.getItem() == Items.fishing_rod) {
//                    if (throwTimer.hasReached(500L) && AutoFish.mc.thePlayer.fishEntity != null) {
//                        if (AutoFish.mc.thePlayer.fishEntity.func_70090_H() || AutoFish.mc.thePlayer.fishEntity.func_180799_ab()) {
//                            if (!this.assistMode.isEnabled() && !this.killPrio.isEnabled()) {
//                                this.findAndSetCurrentSeaCreature();
//                            }
//                            if (!oldBobberInWater) {
//                                if (this.petSwap.isEnabled()) {
//                                    KeybindUtils.stopMovement();
//                                    CF4M.INSTANCE.moduleManager.toggle("PetSwap");
//                                }
//                                oldBobberInWater = true;
//                                inWaterTimer.reset();
//                            }
//                            EntityFishHook bobber = AutoFish.mc.thePlayer.fishEntity;
//                            if (!flash && !inWaterTimer.hasReached(this.slugMode.isEnabled() ? 30000L : 2500L) || !(Math.abs(bobber.field_70159_w) < 0.01 && Math.abs(bobber.field_70179_y) < 0.01) && !flash)
//                                break;
//                            double movement = bobber.field_70163_u - oldBobberPosY;
//                            oldBobberPosY = bobber.field_70163_u;
//                            if (!(movement < -0.04 && this.bobberIsNearParticles(bobber)) && bobber.caughtEntity == null)
//                                break;
//                            this.afs = AutoFishState.FISH_BITE;
//                            break;
//                        }
//                        if (!inWaterTimer.hasReached(2500L) || this.assistMode.isEnabled()) break;
//                        this.afs = AutoFishState.FISH_BITE;
//                        break;
//                    }
//                    if (!throwTimer.hasReached(1000L) || AutoFish.mc.thePlayer.fishEntity != null || this.assistMode.isEnabled())
//                        break;
//                    throwTimer.reset();
//                    this.afs = AutoFishState.THROWING;
//                    break;
//                }
//                if (this.killPrio.isEnabled() && !this.assistMode.isEnabled()) {
//                    RotationUtils.setup(startRot, (long) this.recastDelay.getCurrent());
//                    oldBobberInWater = false;
//                    throwTimer.reset();
//                    this.afs = AutoFishState.THROWING;
//                    break;
//                }
//                if (this.rodSlot.getCurrent() <= 0 || this.rodSlot.getCurrent() > 8 || this.assistMode.isEnabled())
//                    break;
//                AutoFish.mc.thePlayer.inventory.currentItem = this.rodSlot.getCurrent() - 1;
//                break;
//            }
//            case FISH_BITE: {
//                if (!this.assistMode.isEnabled() && this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
//                    AutoFish.mc.thePlayer.inventory.currentItem = this.rodSlot.getCurrent() - 1;
//                }
//                AutoFish.mc.playerController.sendUseItem(AutoFish.mc.thePlayer, AutoFish.mc.theWorld, AutoFish.mc.thePlayer.getHeldItem());
//                if (!this.assistMode.isEnabled()) {
//                    RotationUtils.setup(startRot, (long) this.recastDelay.getCurrent());
//                }
//                oldBobberInWater = false;
//                throwTimer.reset();
//                inWaterTimer.reset();
//                this.afs = this.assistMode.isEnabled() ? AutoFishState.IN_WATER : AutoFishState.THROWING;
//            }
//        }
//    }
//
//    private void findAndSetCurrentSeaCreature() {
//        if (this.whipSlot.getCurrent() != 0) {
//            int ranga = this.scRange.getCurrent();
//            List mobs = AutoFish.mc.theWorld.getEntitiesInAABBexcluding(AutoFish.mc.thePlayer, AutoFish.mc.thePlayer.getEntityBoundingBox().expand(ranga, ranga >> 1, ranga), e -> e instanceof EntityArmorStand);
//            Optional<Entity> filtered = mobs.stream().filter(v -> v.getDistanceToEntity(AutoFish.mc.thePlayer) < (float) ranga && !v.getName().contains(AutoFish.mc.thePlayer.getName()) && fishingMobs.stream().anyMatch(a -> v.getCustomNameTag().contains((CharSequence) a))).min(Comparator.comparing(v -> Float.valueOf(v.getDistanceToEntity(AutoFish.mc.thePlayer))));
//            if (filtered.isPresent()) {
//                curScStand = (EntityArmorStand) filtered.get();
//                curSc = SkyblockUtils.getEntityCuttingOtherEntity(curScStand, null);
//                clicksLeft = (int) Math.ceil((float) SkyblockUtils.getMobHp(curScStand) / (float) this.hypDamage.getCurrent().intValue());
//                if (curSc != null && SkyblockUtils.getMobHp(curScStand) > 0) {
//                    killing = true;
//                    switch (this.killMode.getCurrent()) {
//                        case "Left": {
//                            RotationUtils.setup(RotationUtils.getRotation(curSc.getPositionVector().addVector(0.0, curSc.getEyeHeight(), 0.0)), (long) this.aimSpeed.getCurrent());
//                            break;
//                        }
//                        case "Right": {
//                            RotationUtils.setup(RotationUtils.getRotation(curSc.getPositionVector()), (long) this.aimSpeed.getCurrent());
//                            break;
//                        }
//                        case "Hyperion": {
//                            RotationUtils.setup(new Rotation(AutoFish.mc.thePlayer.rotationYaw, 90.0f), (long) this.aimSpeed.getCurrent());
//                        }
//                    }
//                } else if (SkyblockUtils.getMobHp(curScStand) <= 0) {
//                    curScStand = null;
//                    curSc = null;
//                    if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
//                        AutoFish.mc.thePlayer.inventory.currentItem = this.rodSlot.getCurrent() - 1;
//                    }
//                }
//            } else if (RotationUtils.done) {
//                curScStand = null;
//                curSc = null;
//                if (this.rodSlot.getCurrent() > 0 && this.rodSlot.getCurrent() <= 8) {
//                    AutoFish.mc.thePlayer.inventory.currentItem = this.rodSlot.getCurrent() - 1;
//                }
//            }
//        }
//    }
//
//    @Event
//    public void onPlaySound(SoundEvent event) {
//        if (event.equals("mob.guardian.elder.idle")) {
////            if (this.debug.isEnabled()) {
////                ChatUtils.send("Flash proc detected", new String[0]);
////            }
//            flash = true;
//        }
//    }
//
//    @SubscribeEvent
//    public void onRenderTick(TickEvent.RenderTickEvent event) {
//        if (curSc != null) {
////            Render3DUtils.renderBoundingBox(curSc, event.partialTicks, -16776961);
//        }
//        if (AutoFish.mc.currentScreen != null && !(AutoFish.mc.currentScreen instanceof GuiChat)) {
//            return;
//        }
//        if (!RotationUtils.done) {
//            RotationUtils.update();
//        }
//        if (this.afs == AutoFishState.NAVIGATING && path != null) {
//            switch (this.warpState) {
//                case SETUP: {
//                    if (path.size() > 0) {
//                        if (warpTimer.hasReached(this.warpTime.getCurrent().intValue()) && !AutoFish.mc.thePlayer.getPosition().equals(oldPos)) {
//                            PathPoint a = path.get(0);
//                            path.remove(0);
//                            RotationUtils.setup(RotationUtils.getRotation(new Vec3(a.x, a.y, a.z)), (long) this.warpLookTime.getCurrent());
//                            oldPos = AutoFish.mc.thePlayer.getPosition();
//                            this.warpState = WarpState.LOOK;
//                            break;
//                        }
//                        if (!warpTimer.hasReached(2500L)) break;
//                        ChatUtils.send("Got stuck while tp'ing, re-navigating", new String[0]);
//                        AutoFish.mc.thePlayer.sendChatMessage("/l");
//                        recoverTimer.reset();
//                        warpTimer.reset();
//                        break;
//                    }
//                    if (!warpTimer.hasReached(1000L)) break;
//                    if (!this.sneak.isEnabled()) {
//                        KeyBinding.setKeyBindState((int) AutoFish.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean) false);
//                    }
//                    startRot = AutoFish.currentLocation.rotation;
//                    startPos = AutoFish.mc.thePlayer.getPositionVector();
//                    RotationUtils.setup(startRot, (long) this.recastDelay.getCurrent());
//                    throwTimer.reset();
//                    this.afs = AutoFishState.THROWING;
//                    break;
//                }
//                case LOOK: {
//                    if (System.currentTimeMillis() <= RotationUtils.endTime) {
//                        RotationUtils.update();
//                        break;
//                    }
//                    RotationUtils.update();
//                    warpTimer.reset();
//                    this.warpState = WarpState.WARP;
//                    break;
//                }
//                case WARP: {
//                    if (!warpTimer.hasReached(this.warpTime.getCurrent().intValue())) break;
//                    AutoFish.mc.thePlayer.inventory.currentItem = this.aotvSlot.getCurrent() - 1;
//                    AutoFish.mc.playerController.sendUseItem(AutoFish.mc.thePlayer, AutoFish.mc.theWorld, AutoFish.mc.thePlayer.getHeldItem());
//                    warpTimer.reset();
//                    this.warpState = WarpState.SETUP;
//                }
//            }
//            return;
//        }
//        if (curSc != null) {
//            if (this.killMode.getCurrent().equals("Hyperion")) {
//                if (killTimer.hasReached(125L)) {
//                    if (this.whipSlot.getCurrent() > 0 && this.whipSlot.getCurrent() <= 8) {
//                        AutoFish.mc.thePlayer.inventory.currentItem = this.whipSlot.getCurrent() - 1;
//                    }
//                    if (AutoFish.mc.thePlayer.rotationPitch > 89.0f) {
//                        if (clicksLeft-- > 0) {
//                            KeybindUtils.rightClick();
//                        }
//                        killTimer.reset();
//                    }
//                }
//            } else if (killTimer.hasReached(125L)) {
//                if (this.whipSlot.getCurrent() > 0 && this.whipSlot.getCurrent() <= 8) {
//                    AutoFish.mc.thePlayer.inventory.currentItem = this.whipSlot.getCurrent() - 1;
//                }
//                switch (this.killMode.getCurrent()) {
//                    case "Left": {
//                        KeybindUtils.leftClick();
//                        break;
//                    }
//                    case "Right": {
//                        KeybindUtils.rightClick();
//                    }
//                }
//                killTimer.reset();
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public void onRender2D(Render2DEvent event) {
//        if (!(Minecraft.getMinecraft().currentScreen instanceof ConfigGUI) && Minecraft.getMinecraft().currentScreen != null) {
//            return;
//        }
//        if (this.debug.isEnabled()) {
//            EntityFishHook bobber = AutoFish.mc.thePlayer.fishEntity;
//            double movement = 0.0;
//            if (bobber != null) {
//                movement = bobber.field_70163_u - oldBobberPosY;
//            }
//            boolean first = flash || inWaterTimer.hasReached(this.slugMode.isEnabled() ? 30000L : 2500L);
//            boolean second = flash;
//            if (bobber != null) {
//                second = Math.abs(bobber.field_70159_w) < 0.01 && Math.abs(bobber.field_70179_y) < 0.01 || flash;
//            }
//            ArrayList<String> texts = new ArrayList<String>(Arrays.asList(String.format("InWaterTimer: %d", inWaterTimer.getDelay()), String.format("Flash: %s", flash), String.format("Movement: %f", movement), String.format("First Bool: %s", first), String.format("Second Bool: %s", second)));
//            ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
//            int drawX = (int) ((double) scaled.getScaledWidth() * ((double) this.debugX.getCurrent().intValue() / 100.0));
//            int drawY = (int) ((double) scaled.getScaledHeight() * ((double) this.debugY.getCurrent().intValue() / 100.0));
//            for (String text : texts) {
//                FontUtils.drawChromaString(text, drawX, drawY, drawY);
//                drawY += FontUtils.getFontHeight() + 1;
//            }
//        }
//    }
//
//    @Event
//    public void onWorldUnload(WorldUnloadEvent event) {
//        if (CF4M.INSTANCE.moduleManager.isEnabled(this) && this.fishingSpot.getCurrent().equals("None")) {
//            ChatUtils.send("Your server closed and you didn't have a Spot configured, stopping AutoFish", new String[0]);
//            CF4M.INSTANCE.moduleManager.toggle(this);
//            return;
//        }
//        if (!this.fishingSpot.getCurrent().equals("None") && this.afs != AutoFishState.WARP_ISLAND && this.afs != AutoFishState.WARP_SPOT && this.afs != AutoFishState.NAVIGATING) {
//            this.afs = AutoFishState.THROWING;
//            this.aaState = AAState.AWAY;
//            throwTimer.reset();
//            inWaterTimer.reset();
//            warpTimer.reset();
//            recoverTimer.reset();
//            ticks = 0;
//            oldBobberPosY = 0.0;
//            oldBobberInWater = false;
//            curScStand = null;
//            curSc = null;
//            killing = true;
//            particleList.clear();
//            RotationUtils.reset();
//            this.afs = AutoFishState.WARP_ISLAND;
//            this.warpState = WarpState.SETUP;
//        }
//    }
//
//    @Event
//    public void onChat(ClientChatReceivedEvent event) {
//        if (event.type == 2) {
//            return;
//        }
//        String unformatted = event.message.getUnformattedText();
//        String stripped = StringUtils.stripControlCodes((String) unformatted);
//        if (stripped.startsWith(" ☠ You ")) {
//            new Thread(() -> {
//                CF4M.INSTANCE.moduleManager.toggle(this);
//                ChatUtils.send("Bozo you died while AutoFishing!", new String[0]);
//                if (this.warpOutOnDeath.isEnabled()) {
//                    ChatUtils.send("I'll send you back to your island in 10 seconds... noob", new String[0]);
//                    try {
//                        Thread.sleep(10000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    AutoFish.mc.thePlayer.sendChatMessage("/warp home");
//                }
//            }).start();
//        }
//    }
//
//    public static void handleParticles(S2APacketParticles packet) {
//        if (packet.getParticleType() == EnumParticleTypes.WATER_WAKE || packet.getParticleType() == EnumParticleTypes.SMOKE_NORMAL) {
//            particleList.add(new ParticleEntry(new Vec3(packet.getXCoordinate(), packet.getYCoordinate(), packet.getZCoordinate()), System.currentTimeMillis()));
//        }
//    }
//
//    private boolean bobberIsNearParticles(EntityFishHook bobber) {
//        return particleList.stream().anyMatch(v -> VecUtils.getHorizontalDistance(bobber.func_174791_d(), v.position) < 0.2);
//    }
//
//    private List<String> getFishingSpotNames() {
//        ArrayList<String> ret = new ArrayList<String>();
//        ret.add("None");
//        ret.addAll(AutoFish.fishingJson.locations.stream().map(v -> v.name).collect(Collectors.toList()));
//        return ret;
//    }
//
//    private void resetVariables() {
//        this.afs = AutoFishState.THROWING;
//        this.aaState = AAState.AWAY;
//        throwTimer.reset();
//        inWaterTimer.reset();
//        warpTimer.reset();
//        recoverTimer.reset();
//        ticks = 0;
//        oldBobberPosY = 0.0;
//        oldBobberInWater = false;
//        curScStand = null;
//        curSc = null;
//        killing = true;
//        clicksLeft = 0;
//        flash = false;
//        particleList.clear();
//    }
//
//    private static class ParticleEntry {
//        public Vec3 position;
//        public long timeAdded;
//
//        public ParticleEntry(Vec3 position, long timeAdded) {
//            this.position = position;
//            this.timeAdded = timeAdded;
//        }
//    }
//
//    private static enum AAState {
//        AWAY,
//        BACK;
//
//    }
//
//    private static enum WarpState {
//        SETUP,
//        LOOK,
//        WARP;
//
//    }
//
//    private static enum AutoFishState {
//        WARP_ISLAND,
//        WARP_SPOT,
//        NAVIGATING,
//        THROWING,
//        IN_WATER,
//        FISH_BITE;
//
//    }
//}
//
//
//}
