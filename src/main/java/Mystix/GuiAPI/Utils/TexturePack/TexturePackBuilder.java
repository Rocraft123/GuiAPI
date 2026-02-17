package Mystix.GuiAPI.Utils.TexturePack;

import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
public class TexturePackBuilder {

    private final Path outputFolder;
    private final String namespace;
    private final List<Texture> textures = new ArrayList<>();

    private PackMeta meta = new PackMeta("", PackFormat.R1_21_11_ONLY);

    public TexturePackBuilder(Path outputFolder) {
        this.outputFolder = outputFolder;
        this.namespace = "minecraft";
    }

    public TexturePackBuilder(Path outputFolder, String namespace) {
        this.outputFolder = outputFolder;
        this.namespace = namespace;
    }

    public TexturePackBuilder packMeta(PackMeta meta) {
        this.meta = meta;
        return this;
    }

    public TexturePackBuilder addGuiTexture(String chars, File texture, int ascent, int height) {
        textures.add(new Texture(chars, requireImage(texture), ascent, height));
        return this;
    }

    public Path build() throws Exception {
        return new TexturePackWriter(outputFolder, namespace).write()
                .writePackMeta(meta).writeTextures(textures).finish();
    }

    private File requireImage(File image) {
        if (!image.getName().endsWith(".png"))
            throw new IllegalArgumentException("image must be a png.");
        return image;
    }

    public record Texture(String chars, File image, int ascent, int height) {}
}
