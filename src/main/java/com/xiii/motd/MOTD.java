package com.xiii.motd;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.xiii.motd.listener.ProxyListener;

@Plugin(id = "motd-velocity", name = "MOTD-Velocity", version = "0001", description = "Message Of The Day!", authors = {"XIII___"})
public class MOTD {

    private final ProxyServer server;

    @Inject
    public MOTD(final ProxyServer server) {
        this.server = server;
    }

    @Subscribe
    public void onProxyInitialization(final ProxyInitializeEvent event) {
        this.server.getEventManager().register(this, new ProxyListener());
    }
}
