package com.xiii.motd.listeners;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ProxyListener implements Listener {

    @EventHandler(priority = 64)
    public void onProxyPing(final ProxyPingEvent event) {
        final ServerPing response = event.getResponse();

        if (response == null || event instanceof Cancellable && ((Cancellable) event).isCancelled()) {
            return;
        }

        final ServerPing.Players players = response.getPlayers();

        // Dynamic max player count
        players.setMax(players.getOnline() + 1);

        //Get servers status
        boolean fallbackStatus;
        String motd;

        // Fallback server
        try {
            new Socket().connect(new InetSocketAddress("127.0.0.1", 25565), 1000);
            fallbackStatus = true;
        } catch (final IOException ignored) {
            fallbackStatus = false;
        }

        // Main server
        try {
            new Socket().connect(new InetSocketAddress("192.168.1.99", 25566), 1000);

            // Main server & Fallback server are online
            if (fallbackStatus) {
                motd = "§e§l      ✿§r §6§lWELCOME TO§1§l §b§lGTEAM'S§6§l NETWORK§e§l ✿               §d╚  §a§nProxy§r §2§l✔§a    §nFallback§r§2§l ✔§a    §nMain§r§2§l ✔  §d╝";
            } else { // Main server is online but Fallback server isn't
                motd = "§e§l      ✿§r §6§lWELCOME TO§1§l §b§lGTEAM'S§6§l NETWORK§e§l ✿               §d╚  §a§nProxy§r §2§l✔§c    §nFallback§r§4§l ✖§a    §nMain§r§2§l ✔  §d╝";
            }
        } catch (final IOException ignored) {

            // Main server is offline but Fallback server is online
            if (fallbackStatus) {
                motd = "§e§l      ✿§r §6§lWELCOME TO§1§l §b§lGTEAM'S§6§l NETWORK§e§l ✿               §d╚  §a§nProxy§r §2§l✔§a    §nFallback§r§2§l ✔§c    §nMain§r§4§l ✖  §d╝";
            } else { // Main server & Fallback server are offline
                motd = "§e§l      ✿§r §6§lWELCOME TO§1§l §b§lGTEAM'S§6§l NETWORK§e§l ✿               §d╚  §a§nProxy§r §2§l✔§c    §nFallback§r§4§l ✖§c    §nMain§r§4§l ✖  §d╝";
            }
        }

        // Set MOTD
        response.setDescriptionComponent(new TextComponent(motd));
    }
}
