package io.github.slazurin.sljukeboxloop;

import io.github.slazurin.sljukeboxloop.api.SLJukeboxLoopApi;
import io.github.slazurin.sljukeboxloop.commands.DebugInfo;
import io.github.slazurin.sljukeboxloop.listeners.JukeboxListeners;
import org.bukkit.plugin.java.JavaPlugin;

public class SLJukeboxLoop extends JavaPlugin {
    private SLJukeboxLoopApi api;
    
    @Override
    public void onEnable() {
        this.api = new SLJukeboxLoopApi(this);
        this.registerListeners();
        this.registerCommands();
    }
    
    @Override
    public void onDisable() {
        if (this.api.getLoopCheckerTask() > -1) {
            this.getServer().getScheduler().cancelTask(this.api.getLoopCheckerTask());
        }
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new JukeboxListeners(this), this);
    }

    public SLJukeboxLoopApi getApi() {
        return api;
    }

    private void registerCommands() {
        this.getCommand("debuginfo").setExecutor(new DebugInfo(this));
    }
}
