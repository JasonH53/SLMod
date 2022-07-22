package me.strafe.module;

import java.util.ArrayList;

//import me.strafe.module.fishing.AutoFish;
import me.strafe.StrafeLegitMod;
import me.strafe.module.render.*;
import me.strafe.module.skyblock.*;

public class ModuleManager {

    public static ArrayList<Module> modules;

    public ModuleManager() {
        (modules = new ArrayList<Module>()).clear();
        this.modules.add(new ClickGUI());
//		this.modules.add(new AutoFish());
        this.modules.add(new PlayerDisplayer());
        this.modules.add(new HUD());
        this.modules.add(new HyperionClicker());
        this.modules.add(new ReloadConfig());
        this.modules.add(new GhostBlock());
        this.modules.add(new Velocity());
        this.modules.add(new AutoKuudra());
        this.modules.add(new AntiAfk());
        this.modules.add(new Notifications());
        this.modules.add(new AutoGloom());
        this.modules.add(new EntityReach());
        this.modules.add(new AutoBuy());
        this.modules.add(new ChestStealer());
    }

    public Module getModule(String name) {
        for (Module m : this.modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Module> getModuleList() {
        return this.modules;
    }

    public ArrayList<Module> getModulesInCategory(Category c) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : this.modules) {
            if (m.getCategory() == c) {
                mods.add(m);
            }
        }
        return mods;
    }


}
