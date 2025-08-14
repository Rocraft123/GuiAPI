package Mystix.GuiAPI.Pack.Models;

import java.io.IOException;

public interface Loader<T> {
    T load() throws IOException, ClassNotFoundException;
}
