package com.oresomecraft.forums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ForumGroupUtil {

    public static final String DONATOR_RANK = "Donator";

    public static boolean setUserGroup(String user, String group) {
        try {
            String link = "api.php?action=editUser&hash=" + OresomeForum.apiHash +
                    "&user=" + user + "&group=" + group;

            URL url = new URL(OresomeForum.site + link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Open URL connection
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // User doesn't exist?
                if (inputLine.contains("{\"error\":4,\"message\":\"No user found with the argument: \\\""
                        + user + "\\\"\"}")) return false;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
