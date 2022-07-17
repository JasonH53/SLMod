package me.strafe.module.render;

import me.strafe.config.LoadFile;
import me.strafe.module.Category;
import me.strafe.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class ReloadConfig extends Module {

    public ReloadConfig () {
        super ("Reload Config (TURN OFF WHEN UNUSED)", "Reloads Config", Category.RENDER);
    }

    public void onEnable() {
        super.onEnable();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) throws IOException {
        LoadFile.LoadFile();
    }
}
