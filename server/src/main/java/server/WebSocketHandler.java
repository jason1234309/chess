package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SqlAuthDAO;
import dataaccess.SqlGameDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import responserequest.ErrorResponce;
import websocket.commands.UserGameCommand;

import java.io.IOException;
@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private static SqlAuthDAO sqlAuthObj;
    private static SqlGameDAO sqlGameObj;

    WebSocketHandler(){
        try{
            sqlAuthObj = new SqlAuthDAO();
        }catch(DataAccessException ex){
            System.out.println("Could not create Auth DAO");
        }
        try{
            sqlGameObj = new SqlGameDAO();
        }catch(DataAccessException ex){
            System.out.println("Could not create game DAO");
        }
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            if(command.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE){
                // desirilze to a make move command
            }
            switch (command.getCommandType()) {
                case CONNECT -> connectSocketHandler(session, command);
                case MAKE_MOVE -> makeMoveSocketHandler(session, command);
                case LEAVE -> leaveSocketHandler(session, command);
                case RESIGN -> resignSocketHandler(session, command);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }

    }

    private ErrorResponce connectSocketHandler(Session session, UserGameCommand command){
        connections.add(command.getGameID(), session);
        return new ErrorResponce("not implemented");
    }

    private ErrorResponce makeMoveSocketHandler(Session session, UserGameCommand command){
        // need to implement this
        return new ErrorResponce("not implemented");
    }

    private ErrorResponce leaveSocketHandler(Session session, UserGameCommand command){
        connections.remove(command.getGameID(), session);
        return new ErrorResponce("not implemented");
    }

    private ErrorResponce resignSocketHandler(Session session, UserGameCommand command){
        return new ErrorResponce("not implemented");
    }
}
