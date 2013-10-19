package com.oresomecraft.forums;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class OresomeForum extends JavaPlugin {

    public static String site;
    public static String apiHash;
    public static List<String> admins;

    public void onEnable() {
        saveDefaultConfig();
        site = getConfig().getString("site");
        apiHash = getConfig().getString("apihash");
        admins = getConfig().getStringList("admins");
    }

    public void onDisable() {

    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("register")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Please specify your email address!");
                return false;
            } else {
                Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
                    public void run() {
                        Random ran = new Random();
                        int x = ran.nextInt(Integer.MAX_VALUE) + 999999; // Generate random number password
                        if (RegistrationUtil.registerUser(sender.getName(), x + "", args[0])) {
                            sender.sendMessage(ChatColor.DARK_AQUA + "Successfully registered on the forums!!");
                            sender.sendMessage(ChatColor.DARK_AQUA + "A confirmation email should be " +
                                    "sent to your email address shortly! Please change your password once you confirm your account!");
                            sender.sendMessage(ChatColor.GOLD + "Visit the forums now at: " + ChatColor.AQUA + "http://oresomecraft.com");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Registration failed! An account using the same username or email already exists!");
                        }
                    }
                });
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setforumdonator")) {
            if (!admins.contains(sender.getName())) {
                sender.sendMessage(ChatColor.RED + "Only defined admins can run this command!");
                return false;
            }
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Please specify a forum user!");
                return false;
            } else {
                Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
                    public void run() {
                        if (ForumGroupUtil.setUserGroup(args[0], ForumGroupUtil.DONATOR_RANK)) {
                            sender.sendMessage(ChatColor.DARK_AQUA + "Successfully set user " + args[0]
                                    + " to donator group on the forums!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Failed to set group! User not found!");
                        }
                    }
                });
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("forums")) {
            if (args.length > 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    site = getConfig().getString("site");
                    apiHash = getConfig().getString("apihash");
                    admins = getConfig().getStringList("admins");
                    sender.sendMessage(ChatColor.DARK_AQUA + "Successfully reloaded OresomeForum config!");
                }
            }
            return true;
        }
        return false;
    }

}
