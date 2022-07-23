import me.strafe.StrafeLegitMod;
import me.strafe.commands.Goto;
import me.strafe.events.TickEndEvent;
import me.strafe.module.skyblock.Pathfinding;
import net.minecraftforge.client.ClientCommandHandler;
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
        ClientCommandHandler.instance.registerCommand(new Goto());
        MinecraftForge.EVENT_BUS.register(new Pathfinding());
        MinecraftForge.EVENT_BUS.register(new TickEndEvent());
        me.strafe.utils.Registers.Registers.init();
    }
}
