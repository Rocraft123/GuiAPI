package Mystix.Pack.Loader;

import Mystix.Gui.Entry;
import Mystix.Gui.Gui;
import Mystix.Gui.GuiBuilder;
import Mystix.GuiAPI.GuiAPI;
import Mystix.Pack.Manager.FunctionManager;
import Mystix.Pack.Models.APIFunction;
import Mystix.Pack.Models.GuiPack;
import Mystix.Pack.Models.Loader;
import Mystix.Utils.Serializer;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GuiLoader implements Loader<Gui> {

    private final YamlConfiguration guiConfig;

    private final GuiPack pack;

    public GuiLoader(File file, GuiPack pack) {
        this.guiConfig = YamlConfiguration.loadConfiguration(file);
        this.pack = pack;
    }

    @Override
    public Gui load() throws IOException, ClassNotFoundException, IllegalArgumentException {
        String title = guiConfig.getString("title");
        int size = guiConfig.getInt("size");

        if (title == null) throw new IllegalArgumentException("Could not find a title in " + pack.getName());
        if (size % 9 != 0) throw new IllegalArgumentException("Illegal size, size must be divisible by 9");

        GuiBuilder builder = new GuiBuilder(Component.text(title), size);
        Serializer<ItemStack> serializer = new Serializer<>();

        var entriesSection = guiConfig.getConfigurationSection("Entries");
        if (entriesSection != null) {
            for (String key : entriesSection.getKeys(false)) {
                ConfigurationSection entrySection = entriesSection.getConfigurationSection(key);
                if (entrySection == null) continue;

                String itemStackString = entrySection.getString("ItemStack");
                List<Integer> slots = entrySection.getIntegerList("Slots");
                List<String> slotRanges = entrySection.getStringList("SlotRanges");

                if (itemStackString == null) {
                    GuiAPI.logger.info("Could not find a ItemStack loading entry for: " + pack.getName() + " Gui: " + title);
                    continue;
                }

                ItemStack itemStack = serializer.deserialize(itemStackString);
                Entry entry = new Entry(itemStack);

                List<String> functions = entrySection.getStringList("OnClick");
                if (!functions.isEmpty()) {
                    entry.setOnClick(event -> {
                        for (String functionKey : functions) {
                            APIFunction function = FunctionManager.getFunction(functionKey);
                            String arg = functionKey.split(":", 2)[1];

                            if (function != null)
                                function.activate(event, arg);
                        }
                    });
                }

                Collection<Integer> finalSlots = expandSlots(slots, slotRanges);
                for (int slot : finalSlots)
                    builder.setEntry(slot, entry);
            }
        }

        return builder.build();
    }



    private Collection<Integer> expandSlots(List<Integer> slots, List<String> ranges) {
        Set<Integer> result = new HashSet<>(slots);
        for (String range : ranges) {
            String[] split = range.split("-");
            if (split.length == 2) {
                int start = Integer.parseInt(split[0]);
                int end = Integer.parseInt(split[1]);
                for (int i = start; i <= end; i++)
                    result.add(i);
            }
        }
        return result;
    }


}
