package Mystix.GuiAPI.Utils.TexturePack;

import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
public class TexturePackWriter {

    private final Path outputFolder;
    private final String namespace;

    private Path packRoot;
    private Path fontPath;
    private Path texturesPath;

    public TexturePackWriter(Path outputFolder, String namespace) {
        this.outputFolder = outputFolder;
        this.namespace = namespace;
    }

    public TexturePackWriter write() throws IOException {
        packRoot = outputFolder.resolve("Pack");
        Path assetsPath = packRoot.resolve("assets");
        Path minecraftpath = assetsPath.resolve("minecraft");
        fontPath = minecraftpath.resolve("font");
        texturesPath = assetsPath.resolve(namespace + "/textures/gui");

        Files.createDirectories(fontPath);
        Files.createDirectories(texturesPath);
        return this;
    }

    public TexturePackWriter writePackMeta(PackMeta meta) throws Exception {
        meta.writeTo(packRoot);
        return this;
    }

    public TexturePackWriter writeTextures(List<TexturePackBuilder.Texture> textures) throws IOException {
        List<Map<String, Object>> providers = new ArrayList<>();

        for (TexturePackBuilder.Texture texture : textures) {
            String fileName = texture.image().getName();
            Path outputFile = texturesPath.resolve(fileName);
            Files.copy(texture.image().toPath(), outputFile, StandardCopyOption.REPLACE_EXISTING);

            Map<String, Object> provider = new LinkedHashMap<>();
            provider.put("type", "bitmap");
            provider.put("file", namespace + ":gui/" + fileName);
            provider.put("ascent", texture.ascent());
            provider.put("height", texture.height());

            provider.put("chars", Collections.singletonList(texture.chars()));
            providers.add(provider);
        }

        Map<String, Object> json = new LinkedHashMap<>();
        json.put("providers", providers);

        Path defaultJson = fontPath.resolve("default.json");
        try (Writer writer = Files.newBufferedWriter(defaultJson, StandardCharsets.UTF_8)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(json, writer);
        }

        return this;
    }

    public Path finish() {
        return packRoot;
    }
}
