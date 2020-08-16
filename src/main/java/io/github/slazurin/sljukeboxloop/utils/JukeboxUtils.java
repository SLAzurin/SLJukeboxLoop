package io.github.slazurin.sljukeboxloop.utils;

import io.github.slazurin.sljukeboxloop.api.SLJukeboxLoopApi;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;

public class JukeboxUtils {
    public static final Map<Material,Integer> DISC_DURATIONS;
    static {
        Map<Material,Integer> d = new HashMap<>();
        d.put(Material.MUSIC_DISC_13, 178);
        d.put(Material.MUSIC_DISC_CAT, 185);
        d.put(Material.MUSIC_DISC_BLOCKS, 345);
        d.put(Material.MUSIC_DISC_CHIRP, 185);
        d.put(Material.MUSIC_DISC_FAR, 174);
        d.put(Material.MUSIC_DISC_MALL, 197);
        d.put(Material.MUSIC_DISC_MELLOHI, 96);
        d.put(Material.MUSIC_DISC_STAL, 150);
        d.put(Material.MUSIC_DISC_STRAD, 188);
        d.put(Material.MUSIC_DISC_WARD, 251);
        d.put(Material.MUSIC_DISC_11, 71);
        d.put(Material.MUSIC_DISC_WAIT, 235);
        d.put(Material.MUSIC_DISC_PIGSTEP, 148);
        DISC_DURATIONS = Collections.unmodifiableMap(d);
    }
    
    public static String getJukeboxKey(Jukebox j) {
        return j.getWorld().getName() + SLJukeboxLoopApi.SEPARATOR + j.getX() + "_" + j.getY() + "_" + j.getZ();
    }
    
    public static Jukebox getJukeboxFromKey(String key) {
        String world = key.substring(0, key.lastIndexOf(SLJukeboxLoopApi.SEPARATOR));
        String coords = key.substring(key.lastIndexOf(SLJukeboxLoopApi.SEPARATOR) + 1);
        String[] coordsArr = coords.split("_");
        int x = Integer.parseInt(coordsArr[0]);
        int y = Integer.parseInt(coordsArr[1]);
        int z = Integer.parseInt(coordsArr[2]);
        Location loc = new Location(Bukkit.getWorld(world), x, y, z);
        if (loc.getBlock().getType().equals(Material.JUKEBOX)) {
            Jukebox j = (Jukebox) loc.getBlock().getState();
            return j;
        }
        return null;
    }
}
