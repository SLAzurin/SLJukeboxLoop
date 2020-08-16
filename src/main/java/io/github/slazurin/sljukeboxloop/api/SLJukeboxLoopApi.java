package io.github.slazurin.sljukeboxloop.api;

import io.github.slazurin.sljukeboxloop.SLJukeboxLoop;
import io.github.slazurin.sljukeboxloop.state.JukeboxState;
import io.github.slazurin.sljukeboxloop.utils.JukeboxUtils;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class SLJukeboxLoopApi {
    public static final String SEPARATOR = ".";
    
    private final SLJukeboxLoop plugin;
    private final Map<String,JukeboxState> jukeboxStates;
    private int loopCheckerTask;

    public SLJukeboxLoopApi(SLJukeboxLoop plugin) {
        this.plugin = plugin;
        this.jukeboxStates = new HashMap<>();
        this.loopCheckerTask = -1;
        startLoopChecker();
    }
    
    private void startLoopChecker() {
        this.loopCheckerTask = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            this.jukeboxStates.entrySet().forEach(e -> {
                if (System.currentTimeMillis() - e.getValue().getStartTime() 
                        >= (JukeboxUtils.DISC_DURATIONS.get(e.getValue().getDisc()) * 1000)) {
                    this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        replay(e);
                    });
                }
            });
        }, 0, 5 * 20);
    }
    
    private void replay(Map.Entry<String,JukeboxState> e) {
        Jukebox j = JukeboxUtils.getJukeboxFromKey(e.getKey());
        if (j == null) {
            return;
        }
        String jbkey = e.getKey();
        Material disc = e.getValue().getDisc();
        j.setPlaying(disc);
        j.update();
        
        if (this.jukeboxStates.containsKey(jbkey)) {
            this.jukeboxStates.remove(jbkey);
        }
        this.jukeboxStates.put(jbkey, new JukeboxState(disc, System.currentTimeMillis()));
        
    }
    
    public void delete(Jukebox j) {
        String key = JukeboxUtils.getJukeboxKey(j);
        if (this.jukeboxStates.containsKey(key)) {
            this.jukeboxStates.remove(key);
        }
    }
    
    public void add(Jukebox j, Material disc) {
        if (!new ItemStack(disc).getType().isRecord()) {
            return;
        }
        
        if (j == null) {
            return;
        }
        
        String key = JukeboxUtils.getJukeboxKey(j);
        if (this.jukeboxStates.containsKey(key)) {
            this.jukeboxStates.remove(key);
        }
        this.jukeboxStates.put(key, new JukeboxState(disc, System.currentTimeMillis()));
    }
    
    public void getDebugInfo(CommandSender s) {
        long time = System.currentTimeMillis();
        s.sendMessage(ChatColor.GOLD + "Jukebox info at " + time);
        this.jukeboxStates.entrySet().forEach(e -> {

            s.sendMessage(e.getKey() + ": startTime: " + e.getValue().getStartTime() + " disc: " + e.getValue().getDisc().name());
            Jukebox j = JukeboxUtils.getJukeboxFromKey(e.getKey());
            if (j != null) {
                if (j.isPlaying()) {
                    long elapsedTime = time - e.getValue().getStartTime();
                    long min = elapsedTime/1000/60;
                    long sec = elapsedTime/1000%60;
                    String msg = "Elapsed time: " + min + "m" + (sec < 10 ? "0" + sec : sec) + "s";
                    if (elapsedTime/1000 > JukeboxUtils.DISC_DURATIONS.get(e.getValue().getDisc())) {
                        msg = ChatColor.RED + msg;
                    }
                    s.sendMessage(msg);
                }
            }
        });
    }
    
    

    public int getLoopCheckerTask() {
        return loopCheckerTask;
    }
}
