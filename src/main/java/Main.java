import me.strafe.StrafeLegitMod;
import me.strafe.config.LoadFile;
import me.strafe.events.MotionUpdateEvent;
import me.strafe.module.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.IOException;

@Mod(modid = "autogg", version = "1.0")
public class Main {

    @EventHandler
    public void init (FMLInitializationEvent event) throws IOException {
    	StrafeLegitMod.instance = new StrafeLegitMod();
    	StrafeLegitMod.instance.init();
        me.strafe.utils.Registers.Registers.init();
    }
}
