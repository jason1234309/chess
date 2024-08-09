package websocket.messages;

public class ErrorServerMessage extends ServerMessage{
    String errorMessage;
    public ErrorServerMessage(ServerMessageType type, String message) {
        super(type);
        this.errorMessage = "Error: " +  message;
    }
}
