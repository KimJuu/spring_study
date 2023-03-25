package config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class Config {
//  private static JSONObject config;
//  static {
//    try {
//      config = new JSONObject(new String(Files.readAllBytes(Paths.get("./config.json"))));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  private static InputStream configInputStream = Config.class.getClassLoader().getResourceAsStream("config.json");
  private static InputStreamReader configReader = new InputStreamReader(configInputStream);
  private static JSONObject config = new JSONObject(new JSONTokener(configReader));

  public static String getHttpRoot(String host) {
    JSONArray virtualHostsArr = (JSONArray) config.get("virtualHosts");
    String httpRoot = "";
    for (int i = 0; i < virtualHostsArr.length(); i++) {
      if (host.equals(virtualHostsArr.getJSONObject(i).get("host"))) {
        httpRoot = virtualHostsArr.getJSONObject(i).getString("httpRoot");
      }
    }
    return httpRoot;
  }

  public static int getPort() {
    return config.getInt("port");
  }

  public static String getErrorPage(String httpCode) {
    return config.getJSONObject("errorPages").getString(httpCode);
  }
}
