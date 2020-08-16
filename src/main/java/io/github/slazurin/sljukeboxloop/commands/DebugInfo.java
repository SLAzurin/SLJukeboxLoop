package io.github.slazurin.sljukeboxloop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.slazurin.sljukeboxloop.SLJukeboxLoop;
import org.bukkit.entity.Player;

public class DebugInfo implements CommandExecutor {
    private final SLJukeboxLoop plugin;

    public DebugInfo(SLJukeboxLoop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("sljl.debuginfo")) {
                return false;
            }
        }
        
        this.plugin.getApi().getDebugInfo(sender);
        
        return true;
    }


    
}