package me.roland882.axenvoysaddon;

import org.bukkit.plugin.java.JavaPlugin;

public final class AxEnvoysAddon extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        new PAPI(this).register();
    }

    @Override
    public void onDisable() {
        reloadConfig();
    }
}
