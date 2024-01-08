package net.ethanburkett.serververse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URL;

public class CommandVerify implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("This command can only be used in console.");
            return false;
        }
        if(args.length < 1) {
            Bukkit.getServer().getLogger().warning("You need to provide a token.");
            return false;
        }
        String clerkUserId = args[0];

        HttpClient client = HttpClientBuilder.create().build();
        String url = "http://localhost:3000/api/verify";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("clerk-user-id", clerkUserId);
        try {
            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(responseEntity);

            Bukkit.getServer().getLogger().info(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
