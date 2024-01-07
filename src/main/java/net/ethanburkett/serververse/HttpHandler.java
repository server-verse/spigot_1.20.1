package net.ethanburkett.serververse;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.sun.net.httpserver.HttpExchange;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        handleResponse(httpExchange);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
    }

    private String GetJson(HashMap<String, Object> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    private void handleResponse(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        Server server = Bukkit.getServer();

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", "online");
        map.put("name", server.getName());
        map.put("online", server.getOnlinePlayers().size());
        map.put("max_online", server.getMaxPlayers());


        String response = GetJson(map);
        httpExchange.sendResponseHeaders(200, response.length());

        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
