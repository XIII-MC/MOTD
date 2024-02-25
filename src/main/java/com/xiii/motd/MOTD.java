package com.xiii.motd;

import com.xiii.motd.listeners.ProxyListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class MOTD extends Plugin {

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ProxyListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
