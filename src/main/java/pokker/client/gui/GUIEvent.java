package pokker.client.gui;

public class GUIEvent {
    private final GUIEventType eventType;
    private final Gui GUI;

    public GUIEvent(GUIEventType eventType, Gui GUI) {
        this.eventType = eventType;
        this.GUI = GUI;
    }

    public Gui getGUI() {
        return GUI;
    }

    public GUIEventType getEventType() {
        return eventType;
    }
}
