package net.ethanburkett.serververse;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class HttpVerify implements com.sun.net.httpserver.HttpHandler {
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

        Headers headers = httpExchange.getRequestHeaders();
        String clerkUserId = headers.get("clerk-user-id").get(0);

        String msg = "Please run /verify " + clerkUserId + " to confirm the connection between ServerVerse and your server.";
        Bukkit.getServer().getLogger().info(msg);

        HashMap<String, Object> map = new HashMap<>();
        map.put("success", true);

        String response = GetJson(map);
        httpExchange.sendResponseHeaders(200, response.length());

        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
