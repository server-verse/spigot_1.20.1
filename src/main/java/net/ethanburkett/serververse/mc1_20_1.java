package net.ethanburkett.serververse;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public final class mc1_20_1 extends JavaPlugin {
    HttpServer server;

    @Override
    public void onEnable() {
        // Plugin startup logic
        String pluginFile = "config.yml";
        String dataFolder = "plugins/serververse/";
        File configFile = new File(dataFolder, pluginFile);

        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 25578), 0);
            server.createContext("/status", new HttpHandler());

            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            server.setExecutor(threadPoolExecutor);
            server.start();
            getLogger().info("Started ServerVerse tools on :25578");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Attempting to shut down ServerVerse socket.");
        server.stop(3);
    }
}
