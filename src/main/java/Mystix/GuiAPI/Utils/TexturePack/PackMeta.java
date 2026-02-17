package Mystix.GuiAPI.Utils.TexturePack;

import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.ApiStatus;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * EXPERIMENTAL – INTERNAL API
 *
 * <p>This class is under active development and is not safe for public use.
 * Behavior, signatures, and output may change without notice.</p>
 *
 * <p>Using this class in production code is unsupported.</p>
 */

@ApiStatus.Experimental
@ApiStatus.Internal
@Deprecated(forRemoval = false, since = "0.1.0-alpha")
public class PackMeta {

    private final PackSection pack;
    private Features features;
    private Filter filter;
    private Overlays overlays;
    private Map<String, LanguageEntry> language;

    public PackMeta(String description, double packFormat) {
        this.pack = new PackSection(description, packFormat);
    }

    public static class PackSection {
        private final Object description;
        private final double pack_format;
        private Object supported_formats;

        public PackSection(String description, double pack_format) {
            this.description = description;
            this.pack_format = pack_format;
        }

        public PackSection supportedFormats(int value) {
            this.supported_formats = value;
            return this;
        }

        public PackSection supportedFormats(int min, int max) {
            this.supported_formats = Arrays.asList(min, max);
            return this;
        }

        public PackSection supportedFormatsRange(int min, int max) {
            Map<String, Integer> obj = new HashMap<>();
            obj.put("min_inclusive", min);
            obj.put("max_inclusive", max);
            this.supported_formats = obj;
            return this;
        }
    }

    public static class Features {
        private final List<String> enabled = new ArrayList<>();

        public Features enable(String feature) {
            enabled.add(feature);
            return this;
        }
    }

    public static class Filter {
        private final List<PatternEntry> block = new ArrayList<>();

        public Filter block(String namespace, String path) {
            block.add(new PatternEntry(namespace, path));
            return this;
        }

        public record PatternEntry(String namespace, String path) {
        }
    }

    public static class Overlays {
        private final List<OverlayEntry> entries = new ArrayList<>();

        public Overlays addOverlay(Object formats, String directory) {
            entries.add(new OverlayEntry(formats, directory));
            return this;
        }

        public record OverlayEntry(Object formats, String directory) { }
    }

    public record LanguageEntry(String name, String region, boolean bidirectional) { }

    public PackMeta features(Features features) {
        this.features = features;
        return this;
    }

    public PackMeta filter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public PackMeta overlays(Overlays overlays) {
        this.overlays = overlays;
        return this;
    }

    public PackMeta language(String code, LanguageEntry entry) {
        if (this.language == null) this.language = new LinkedHashMap<>();
        this.language.put(code, entry);
        return this;
    }

    public void writeTo(Path packRoot) throws Exception {
        Path metaFile = packRoot.resolve("pack.mcmeta");

        Map<String, Object> root = new LinkedHashMap<>();
        root.put("pack", pack);
        if (features != null) root.put("features", features);
        if (filter != null) root.put("filter", filter);
        if (overlays != null) root.put("overlays", overlays);
        if (language != null) root.put("language", language);

        String json = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create()
                .toJson(root);

        Files.writeString(metaFile, json, StandardCharsets.UTF_8);
    }
}


