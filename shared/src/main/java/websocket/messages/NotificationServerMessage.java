package websocket.messages;

public class NotificationServerMessage extends ServerMessage{
    String notificationString;
    public NotificationServerMessage(ServerMessageType type, String message) {
        super(type);
        notificationString = message;
    }
}
