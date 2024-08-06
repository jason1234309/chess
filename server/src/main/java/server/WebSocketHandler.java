package server;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import responserequest.ErrorResponce;
import server.ConnectionManager;
import websocket.commands.UserGameCommand;

import java.io.IOException;
@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

//            // Throws a custom UnauthorizedException. Yours may work differently.
//            String username = getUsername(command.getAuthString());
//
//            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connectSocketHandler(session, username, command);
                case MAKE_MOVE -> makeMoveSocketHandler(session, username, command);
                case LEAVE -> leaveSocketHandler(session, username, command);
                case RESIGN -> resignSocketHandler(session, username, command);
            }
        } catch (UnauthorizedException ex) {
            // Serializes and sends the error message
            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }

    }

    private ErrorResponce connectSocketHandler(Session session, String username, UserGameCommand command){
        connections.add(username, session);
        return new ErrorResponce("not implemented");
    }

    private ErrorResponce makeMoveSocketHandler(Session session, String username, UserGameCommand command){
        return new ErrorResponce("not implemented");
    }

    private ErrorResponce leaveSocketHandler(Session session, String username, UserGameCommand command){
        connections.remove(username);
        return new ErrorResponce("not implemented");
    }

    private ErrorResponce resignSocketHandler(Session session, String username, UserGameCommand command){
        return new ErrorResponce("not implemented");
    }
}
