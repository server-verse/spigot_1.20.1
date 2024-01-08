package net.ethanburkett.serververse;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public final class mc1_20_1 extends JavaPlugin {
    HttpServer server;

    private boolean WriteToConfig(String key, Object value) throws IOException {
        String pluginFile = "config.yml";
        String dataFolderPath = "plugins/serververse/";
        File configFile = new File(dataFolderPath, pluginFile);
        FileWriter fw = new FileWriter(configFile.getAbsolutePath());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(key + ": " + value);
        bw.close();
        return true;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        String pluginFile = "config.yml";
        String dataFolderPath = "plugins/serververse/";
        File dataFolder = new File(String.valueOf(dataFolderPath));
        File configFile = new File(dataFolderPath, pluginFile);

        this.getCommand("verify").setExecutor(new CommandVerify());

        try {
            if (!dataFolder.exists()) {
                Boolean success = dataFolder.mkdir();
            }
            if (!configFile.exists()) {
                Boolean success = configFile.createNewFile();
                WriteToConfig("port", 25577);
            }
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(configFile);
            int port = yaml.getInt("port");

            server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
            server.createContext("/status", new HttpStatus());
            server.createContext("/verify", new HttpVerify());

            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            server.setExecutor(threadPoolExecutor);
            server.start();
            getLogger().info("Started ServerVerse tools on :" + port);
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
