package io.github.slazurin.sljukeboxloop.listeners;

import io.github.slazurin.sljukeboxloop.SLJukeboxLoop;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class JukeboxListeners implements Listener {
    private final SLJukeboxLoop plugin;

    public JukeboxListeners(SLJukeboxLoop plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJukeboxInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block interactBlock = event.getClickedBlock();
        
        if (interactBlock == null) {
            return;
        }
        
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && interactBlock.getType().equals(Material.JUKEBOX))) {
            return;
        }
        
        Jukebox j = (Jukebox) interactBlock.getState();
        
        if (!j.getRecord().getType().equals(Material.AIR)) {
            this.plugin.getApi().delete(j);
            j.eject();
        } else {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (itemInMainHand.getType().isRecord()) {
                this.plugin.getApi().add(j, itemInMainHand.getType());
            }
        }
    }
    
    @EventHandler
    public void onJukeboxBreak(BlockBreakEvent event) {
        Jukebox j = (Jukebox) event.getBlock().getState();

        this.plugin.getApi().delete(j);
    }
}
