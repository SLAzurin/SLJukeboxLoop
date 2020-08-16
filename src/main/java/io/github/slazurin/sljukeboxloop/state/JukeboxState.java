package io.github.slazurin.sljukeboxloop.state;

import org.bukkit.Material;

public class JukeboxState {
    private Material disc;
    private long startTime;

    public JukeboxState() {
    }

    public JukeboxState(Material disc, long startTime) {
        this.disc = disc;
        this.startTime = startTime;
    }

    public Material getDisc() {
        return disc;
    }

    public void setDisc(Material disc) {
        this.disc = disc;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
