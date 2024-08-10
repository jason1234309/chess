package websocket.messages;

public class ErrorServerMessage extends ServerMessage{
    public String errorMessage;
    public ErrorServerMessage(ServerMessageType type, String message) {
        super(type);
        this.errorMessage = "Error: " +  message;
    }
}
