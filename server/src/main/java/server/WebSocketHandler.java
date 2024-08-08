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
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Set;

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

    private void connectSocketHandler(Session session, UserGameCommand command){
        connections.add(command.getGameID(), session);
        new ErrorResponce("not implemented");
    }

    private void makeMoveSocketHandler(Session session, UserGameCommand command){
        // need to implement this
    }

    private void leaveSocketHandler(Session session, UserGameCommand command){
        connections.remove(command.getGameID(), session);
    }

    private void resignSocketHandler(Session session, UserGameCommand command) throws IOException {

    }

    public void sendMessage(Session session, ServerMessage message) throws IOException {
        session.getRemote().sendString(" ");
    }
    public void broadCast(Integer gameId, Session excludedSession, ServerMessage broadCastMessage) throws IOException {
        Set<Session> currentGameSessionSet = connections.getGameSessions(gameId);
        if(currentGameSessionSet != null){
            for( Session currentSession: currentGameSessionSet){
                if(!currentSession.equals(excludedSession)){
                    this.sendMessage(currentSession, broadCastMessage);
                }
            }
        }

    }
}
