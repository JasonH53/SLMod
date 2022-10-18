package me.strafe.module.skyblock;

import me.strafe.module.Category;
import me.strafe.module.Module;

public class Velocity extends Module {

    private static final String yepCock = "yepCock" + Math.log(3.0) / 4.0;

    public Velocity () {
        super("Lava AntiKB", "KB modifier", Category.SKYBLOCK);
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Horizontal", this, 0, 0, 100, true));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Vertical", this, 0, 0, 100, true));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Lava mode", this, false));
    }

    public void onEnable() {
        super.onEnable();
    }
}
