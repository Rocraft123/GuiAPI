package Mystix.GuiAPI.Utils.ResourcePack;

import org.bukkit.entity.Player;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class ResourcePack {

    private final File pack;
    private final UUID id;
    private final URL url;

    public ResourcePack(File pack) throws MalformedURLException {
        this(pack, UUID.randomUUID());
    }

    public ResourcePack(File pack, UUID id) throws MalformedURLException {
        this.pack = pack;
        this.id = id;
        this.url = pack.toURI().toURL();
    }

    public void apply(Player player) {
        player.addResourcePack(id, url.toString(), new byte[]{}, "add a good prompt message here", false);
    }

    public void apply(Player player, String prompt, boolean force) {
        player.addResourcePack(id, url.toString(), new byte[]{}, prompt, force);
    }

    public UUID getId() {
        return id;
    }

    public File getPack() {
        return pack;
    }
}
