package com.oresomecraft.forums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Code forked from RegistrationUtil. Developed my myself anyway.
 * @author Zachoz
 */
public class RegistrationUtil {

    /**
     * Registers a user on the forums
     *
     * @param user     Username of the user
     * @param password Password for the user. A randomly generated one is recommended.
     * @param email    Email address for the user
     * @return True if reqistration was successful
     */
    public static boolean registerUser(String user, String password, String email) {
        try {
            String link = "api.php?action=register&hash=" + OresomeForum.apiHash + "&username=" + user +
                    "&password=" + password + "&email=" + email + "&custom_fields=minecraftusername=" + user
                    + "&user_state=email_confirm";

            URL url = new URL(OresomeForum.site + link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Open URL connection
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // User already exists?
                if (inputLine.contains("{\"error\":7,\"message\":\"Something went wrong when \\\"registering user\\\": \\\"" +
                        "User already exists\\\"\",\"user_error_id\":40,\"user_error_field\":\"username\",\"" +
                        "user_error_key\":\"usernames_must_be_unique\",\"user_error_phrase\":\"Usernames must be unique." +
                        " The specified username is already in use.\"}")) return false;

                // Email already in use?
                if (inputLine.contains("{\"error\":7,\"message\":\"Something went wrong when \\\"registering user\\\": \\\"" +
                        "Email already used\\\"\",\"user_error_id\":42,\"user_error_field\":\"email\",\"user_error_key\":\"" +
                        "email_addresses_must_be_unique\",\"user_error_phrase\":\"Email addresses must be unique. " +
                        "The specified email address is already in use.\"}")) return false;
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

