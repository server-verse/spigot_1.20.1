package net.ethanburkett.serververse;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        String response = "OK";
        httpExchange.sendResponseHeaders(200, response.length());

        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
