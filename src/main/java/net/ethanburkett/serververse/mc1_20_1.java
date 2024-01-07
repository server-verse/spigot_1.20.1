package net.ethanburkett.serververse;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.configuration.file.YamlConfiguration;
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
        String dataFolderPath = "plugins/serververse/";
        File dataFolder = new File(String.valueOf(dataFolderPath));
        File configFile = new File(dataFolderPath, pluginFile);

        try {
            if(!dataFolder.exists()) {
                dataFolder.mkdir();
                if(!configFile.exists()) {
                    configFile.createNewFile();
                }
            }
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(configFile);

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
