package Mystix.Utils;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class Serializer<T> {

    public String serialize(T object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
        dataOutput.writeObject(object);
        dataOutput.close();
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @SuppressWarnings("unchecked")
    public T deserialize(String base64) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(base64);
        ObjectInputStream dataInput = new BukkitObjectInputStream(new ByteArrayInputStream(data));

        return (T) dataInput.readObject();
    }
}
