package me.strafe.module.render;

import org.lwjgl.input.Keyboard;

import me.strafe.StrafeLegitMod;
import me.strafe.module.Category;
import me.strafe.module.Module;

public class ClickGUI extends Module {

	public ClickGUI() {
		super("ClickGUI", "TEST FEATURE", Category.RENDER);
		this.setKey(Keyboard.KEY_RCONTROL);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		mc.displayGuiScreen(StrafeLegitMod.instance.clickGui);
		this.setToggled(false);
	}
}
