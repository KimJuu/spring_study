package webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebServer_back {
    private static final Logger logger = LoggerFactory.getLogger(WebServer_back.class);
    private static JSONObject config;

    public static void main(String args[]) throws Exception {
        String configContent = new String(Files.readAllBytes(Paths.get("config.json")));
        config = new JSONObject(configContent);

        int port = config.getInt("port");
        // Set the document root
        String documentRoot = config.getString("httpRoot");

        // Create an HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        logger.info("Web Application Server started {} port.", port);

        // Set up a default handler
        server.createContext("/", exchange -> {
            // Get the requested file path
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }

            Path filePath = Paths.get(documentRoot, path);

            try {
                // Serve the requested file
                byte[] fileContent = Files.readAllBytes(filePath);
                exchange.sendResponseHeaders(200, fileContent.length);
                OutputStream out = exchange.getResponseBody();
                out.write(fileContent);
                out.close();
            } catch (IOException e) {
                // Handle file not found (404) or other errors
                handleError(exchange, filePath, e);
            }
        });

        server.start();

    }

    private static void handleError(HttpExchange exchange, Path filePath, IOException e) {
        try {
            // Handle different HTTP status codes
            int statusCode;
            String errorPage;

            if (!Files.exists(filePath)) {
                statusCode = 404;
            } else {
                statusCode = 500;
            }
            errorPage = (String) config.get(String.valueOf(statusCode));

            Path errorFilePath = Paths.get(config.getString("httpRoot"), errorPage);
            byte[] errorContent = Files.readAllBytes(errorFilePath);
            exchange.sendResponseHeaders(statusCode, errorContent.length);
            OutputStream out = exchange.getResponseBody();
            out.write(errorContent);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}