package com.xiii.motd.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import net.kyori.adventure.text.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ProxyListener {

    @Subscribe
    public void onProxyPing(final ProxyPingEvent event) {

        final ServerPing ping = event.getPing();
        final ServerPing.Builder pingBuilder = ping.asBuilder();

        final boolean fallbackStatus = checkServer("127.0.0.1", 25565);
        final boolean mainStatus = checkServer("192.168.1.250", 25570);

        final String motd;

        if (fallbackStatus && mainStatus) {

            motd = formatMOTD("✔", "✔");

        } else if (fallbackStatus) {

            motd = formatMOTD("✔", "✖");

        } else if (mainStatus) {

            motd = formatMOTD("✖", "✔");

        } else {

            motd = formatMOTD("✖", "✖");
        }

        // Update MOTD
        pingBuilder.description(Component.text(motd));

        // Update maximum player count
        pingBuilder.maximumPlayers(pingBuilder.getOnlinePlayers() + 1);

        event.setPing(pingBuilder.build());
    }

    private boolean checkServer(final String ip, final int port) {

        try (final Socket socket = new Socket()) {

            socket.connect(new InetSocketAddress(ip, port), 1000);

            return true;

        } catch (final IOException ignored) {

            return false;

        }
    }

    private String formatMOTD(final String fallbackStatus, final String mainStatus) {
        return String.format("§e§l      ✿§r §6§lWELCOME TO§1§l §b§lGTEAM'S§6§l NETWORK§e§l ✿               §d╚  §a§nProxy§r §2§l%s§a    §nFallback§r§2§l %s§a    §nMain§r§2§l %s  §d╝",
                "✔", fallbackStatus, mainStatus);
    }

}
