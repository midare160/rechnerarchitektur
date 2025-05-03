package com.lhmd.rechnerarchitektur.events;

import javafx.event.*;

public class MainMenuBarEvent<T> extends Event {
    public static final EventType<MainMenuBarEvent<?>> ANY = new EventType<>(Event.ANY, "ANY");

    public static final EventType<MainMenuBarEvent<String>> ON_FILE_OPENED = new EventType<>(MainMenuBarEvent.ANY, "MAIN_MENUBAR_FILE_OPENED");
    public static final EventType<MainMenuBarEvent<Void>> ON_FILE_CLOSED = new EventType<>(MainMenuBarEvent.ANY, "MAIN_MENUBAR_FILE_CLOSED");

    public static final EventType<MainMenuBarEvent<Void>> ON_RUN = new EventType<>(MainMenuBarEvent.ANY, "MAIN_MENUBAR_RUN");
    public static final EventType<MainMenuBarEvent<Void>> ON_RESET = new EventType<>(MainMenuBarEvent.ANY, "MAIN_MENUBAR_RESET");
    public static final EventType<MainMenuBarEvent<Void>> ON_PAUSE = new EventType<>(MainMenuBarEvent.ANY, "MAIN_MENUBAR_PAUSE");
    public static final EventType<MainMenuBarEvent<Void>> ON_NEXT = new EventType<>(MainMenuBarEvent.ANY, "MAIN_MENUBAR_NEXT");

    private final T data;

    public MainMenuBarEvent(EventType<? extends Event> eventType){
        this(eventType, null);
    }

    public MainMenuBarEvent(EventType<? extends Event> eventType, T data) {
        super(eventType);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
