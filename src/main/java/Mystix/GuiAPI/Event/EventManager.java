package Mystix.GuiAPI.Event;

import Mystix.GuiAPI.InitializedGuiAPI;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class EventManager {

    private final Map<Class<? extends Event>, List<RegisteredHandler<?>>> handlersMap = new LinkedHashMap<>();
    private final InitializedGuiAPI guiAPI;

    public EventManager(InitializedGuiAPI guiAPI) {
        this.guiAPI = guiAPI;
    }

    @SuppressWarnings("unchecked")
    public void callEvent(Event event) {
        Class<?> clazz = event.getClass();

        while (clazz != null && Event.class.isAssignableFrom(clazz)) {
            List<RegisteredHandler<?>> handlers = handlersMap.getOrDefault(clazz, Collections.emptyList());

            for (RegisteredHandler<?> handler : handlers)
                ((Consumer<Event>) handler.consumer).accept(event);

            clazz = clazz.getSuperclass();
        }
    }

    public <T extends Event> RegisteredHandler<T> register(@NotNull Class<T> clazz, @NotNull Consumer<T> consumer) {
        RegisteredHandler<T> handler = new RegisteredHandler<>(consumer);
        handlersMap.computeIfAbsent(clazz, c -> new ArrayList<>())
                .add(handler);
        return handler;
    }

    public void unregister(@NotNull RegisteredHandler<? extends Event> handler) {
        handlersMap.values().forEach(handlers ->
                handlers.removeIf(handler::equals));
    }

    public void unregisterAll(@NotNull Class<Event> clazz) {
        handlersMap.getOrDefault(clazz, new ArrayList<>()).clear();
    }

    public void unregisterAll() {
        handlersMap.clear();
    }

    public record RegisteredHandler<T extends Event>(Consumer<T> consumer) {}
}
