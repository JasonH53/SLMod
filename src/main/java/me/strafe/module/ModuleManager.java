package me.strafe.module;

import java.util.ArrayList;

import me.strafe.module.fishing.AutoFish;
import me.strafe.module.render.ClickGUI;
import me.strafe.module.render.HUD;
import me.strafe.module.render.PlayerDisplayer;
import me.strafe.module.render.ReloadConfig;
import me.strafe.module.skyblock.AutoKuudra;
import me.strafe.module.skyblock.GhostBlock;
import me.strafe.module.skyblock.HyperionClicker;
import me.strafe.module.skyblock.Velocity;

public class ModuleManager {

	public ArrayList<Module> modules;
	
	public ModuleManager() {
		(modules = new ArrayList<Module>()).clear();
		this.modules.add(new ClickGUI());
		this.modules.add(new AutoFish());
		this.modules.add(new PlayerDisplayer());
		this.modules.add(new HUD());
		this.modules.add(new HyperionClicker());
		this.modules.add(new ReloadConfig());
		this.modules.add(new GhostBlock());
		this.modules.add(new Velocity());
//		this.modules.add(new AutoKuudra());
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