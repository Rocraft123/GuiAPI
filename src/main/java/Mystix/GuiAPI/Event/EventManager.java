package Mystix.GuiAPI.Event;

import Mystix.GuiAPI.InitializedGuiAPI;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

public class EventManager {

    private final Map<Class<? extends Event>, Set<RegisteredHandler<?>>> handlersMap = new HashMap<>();
    private final InitializedGuiAPI guiAPI;

    public EventManager(InitializedGuiAPI guiAPI) {
        this.guiAPI = guiAPI;
    }

    @SuppressWarnings("unchecked")
    public void callEvent(Event event) {
        Class<?> clazz = event.getClass();

        guiAPI.debug(Level.INFO,"Event called: " + event.getClass().getSimpleName());

        while (clazz != null && Event.class.isAssignableFrom(clazz)) {
            Set<RegisteredHandler<?>> handlers = handlersMap.getOrDefault(clazz, Collections.emptySet());

            for (RegisteredHandler<?> handler : handlers)
                ((Consumer<Event>) handler.consumer).accept(event);

            clazz = clazz.getSuperclass();
        }
    }

    public <T extends Event> RegisteredHandler<T> register(@NotNull Class<T> clazz, @NotNull Consumer<T> consumer) {
        RegisteredHandler<T> handler = new RegisteredHandler<>(consumer);
        handlersMap.computeIfAbsent(clazz, c -> new HashSet<>())
                .add(handler);
        return handler;
    }

    public void unregister(@NotNull RegisteredHandler<? extends Event> handler) {
        handlersMap.values().forEach(handlers ->
                handlers.removeIf(handler::equals));
    }

    public void unregisterAll(@NotNull Class<? extends Event> clazz) {
        handlersMap.remove(clazz);
    }

    public void unregisterAll() {
        handlersMap.clear();
    }

    public record RegisteredHandler<T extends Event>(Consumer<T> consumer) {}
}
