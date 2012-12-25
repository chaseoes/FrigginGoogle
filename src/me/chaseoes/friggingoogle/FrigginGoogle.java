package me.chaseoes.friggingoogle;

import me.chaseoes.friggingoogle.shorteners.GoogleShortener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Joiner;

public class FrigginGoogle extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().header("FrigginGoogle v" + getDescription().getVersion() + " by chaseoes.");
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (cmnd.getName().equalsIgnoreCase("google")) {
            if (cs.hasPermission("friggingoogle.google")) {
                if (strings.length > 0) {
                    String searchTerms = Joiner.on(" ").join(strings);
                    String url = "https://www.google.com/search?q=" + searchTerms.replace("-c", "").trim();

                    // Is there a more thread-safe way to do this?
                    if (getConfig().getBoolean("output-full-url")) {
                        if (!searchTerms.contains("-c")) {
                            cs.sendMessage("§3" + url);
                        } else {
                            if (cs instanceof Player) {
                                Player player = (Player) cs;
                                player.chat(url);
                            } else {
                                cs.sendMessage("You can only use the '-c' flag as a player!");
                            }
                        }
                    } else {
                        try {
                            GoogleShortener gs = new GoogleShortener(url);
                            if (!searchTerms.contains("-c")) {
                                cs.sendMessage("§3" + gs.shorten());
                            } else {
                                if (cs instanceof Player) {
                                    Player player = (Player) cs;
                                    player.chat(gs.shorten());
                                } else {
                                    cs.sendMessage("You can only use the '-c' flag as a player!");
                                }
                            }
                        } catch (Exception e) {
                            cs.sendMessage("§cError encountered while shortening link.");
                            e.printStackTrace();
                        }
                    }
                } else {
                    cs.sendMessage("§cUsage: /google <search terms>");
                }
            } else {
                cs.sendMessage("§cYou don't have permission for that.");
            }
        }
        return true;
    }

}
