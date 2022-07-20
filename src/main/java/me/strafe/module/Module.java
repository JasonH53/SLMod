package me.strafe.module;

import com.google.gson.JsonObject;
import me.strafe.StrafeLegitMod;
import me.strafe.config.SaveLoad;
import me.strafe.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;

public class Module {

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	private String name, description;
	private int key;
	private Category category;
	private boolean toggled;
	public boolean visible = true;


	public Module(String name, String description, Category category) {
		super();
		this.name = name;
		this.description = description;
		this.key = 0;
		this.category = category;
		this.toggled = false;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
		if (StrafeLegitMod.instance.saveLoad != null) {
			StrafeLegitMod.instance.saveLoad.save();
		}
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		
		if (this.toggled) {
			this.onEnable();
		} else {
			this.onDisable();
		}
	}
	
	public void toggle() {
		this.toggled = !this.toggled;
		
		if (this.toggled) {
			this.onEnable();
		} else {
			this.onDisable();
		}
		if (StrafeLegitMod.instance.saveLoad != null) {
			StrafeLegitMod.instance.saveLoad.save();
		}
	}
	
	public void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Category getCategory() {
		return this.category;
	}

	public void enableOnStartup() {
		toggled = true;
		try {
			toggle();
			onEnable();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


}
