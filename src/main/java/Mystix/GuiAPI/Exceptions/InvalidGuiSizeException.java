package Mystix.GuiAPI.Exceptions;

public class InvalidGuiSizeException extends IllegalArgumentException {

    public InvalidGuiSizeException(int size) {
        super("Invalid GUI size: " + size + ". Size must be a multiple of 9.");
    }
}
