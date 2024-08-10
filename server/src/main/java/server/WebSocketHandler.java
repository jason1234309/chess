package server;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.DataAccessException;
import dataaccess.SqlAuthDAO;
import dataaccess.SqlGameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorServerMessage;
import websocket.messages.LoadGameServerMessage;
import websocket.messages.NotificationServerMessage;
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
        MakeMoveCommand moveCommand = null;
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            switch (command.getCommandType()) {
                case CONNECT -> connectSocketHandler(session, command);
                case MAKE_MOVE -> {
                    moveCommand = new Gson().fromJson(message, MakeMoveCommand.class);
                    makeMoveSocketHandler(session, moveCommand);
                }
                case LEAVE -> leaveSocketHandler(session, command);
                case RESIGN -> resignSocketHandler(session, command);
            }
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }

    }
    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) throws DataAccessException {
        connections.removeFromAllGames(session);
    }
    @OnWebSocketError
    public void onError(Throwable ex){
        System.out.println(ex.getMessage());
    }


    private void connectSocketHandler(Session session, UserGameCommand command) throws IOException {
        try{
            if(sqlAuthObj.getAuth(command.getAuthToken()) != null){
                AuthData sessionAuth = sqlAuthObj.getAuth(command.getAuthToken());
                if(sqlGameObj.getGame(command.getGameID()) != null){
                    connections.add(command.getGameID(), session);
                    this.sendMessage(session,  new LoadGameServerMessage(ServerMessage.ServerMessageType.LOAD_GAME,
                            sqlGameObj.getGame(command.getGameID()).getChessGame()));
                    if(sqlGameObj.getGame(command.getGameID()).getWhiteUsername() != null &&
                    sqlGameObj.getGame(command.getGameID()).getBlackUsername() != null){
                        if(sqlGameObj.getGame(command.getGameID()).getWhiteUsername().equals(sessionAuth.getUsername())){
                            this.broadCast(command.getGameID(), session, new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the white player joined the game"));
                        }else if(sqlGameObj.getGame(command.getGameID()).getBlackUsername().equals(sessionAuth.getUsername())){
                            this.broadCast(command.getGameID(), session, new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the black player joined the game"));
                        }else{
                            this.broadCast(command.getGameID(), session, new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,"Observer " +
                                    sessionAuth.getUsername() + " joined the game"));
                        }
                    }else{
                        this.broadCast(command.getGameID(), session, new NotificationServerMessage(
                                ServerMessage.ServerMessageType.NOTIFICATION,"Observer " +
                                sessionAuth.getUsername() + " joined the game"));
                    }
                }else{
                    throw new DataAccessException("unauthorized");
                }
            }else{
                throw new DataAccessException("unauthorized");
            }
        }catch( Exception ex){
            this.sendMessage(session, new ErrorServerMessage(ServerMessage.ServerMessageType.ERROR, ex.getMessage()));
        }
    }

    private void makeMoveSocketHandler(Session session, MakeMoveCommand command) throws IOException {
        try{
            if(sqlAuthObj.getAuth(command.getAuthToken()) != null){
                AuthData sessionAuth = sqlAuthObj.getAuth(command.getAuthToken());
                if(sqlGameObj.getGame(command.getGameID()) != null){
                    if(!connections.getGameSessions(command.getGameID()).contains(session)){
                        throw new DataAccessException("You are not connected to that game");
                    }
                    GameData currentGame = sqlGameObj.getGame(command.getGameID());
                    if(currentGame.getChessGame().isGameHasEnded()){
                        throw new DataAccessException("the game has ended, no moves can be made");
                    }
                    if(currentGame.getWhiteUsername() != null &&
                    currentGame.getWhiteUsername().equals(sessionAuth.getUsername())){
                        // Check white player move
                        if(currentGame.getChessGame().getTeamTurn() != ChessGame.TeamColor.WHITE){
                            throw new DataAccessException("It is not your turn");
                        }
                        try{
                            if(currentGame.getChessGame().validMoves(command.getChessMove().getStartPosition()) == null){
                                throw new DataAccessException("that piece has no valid moves");
                            }
                            if(!currentGame.getChessGame().validMoves(command.getChessMove().
                                    getStartPosition()).contains(command.getChessMove())){
                                throw new DataAccessException("that move is invalid");
                            }
                            currentGame.getChessGame().makeMove(command.getChessMove());

                            this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the white player make a move"));

                            this.broadCast(command.getGameID(), null, new LoadGameServerMessage(
                                    ServerMessage.ServerMessageType.LOAD_GAME,currentGame.getChessGame()));

                            if(currentGame.getChessGame().isInCheckmate(ChessGame.TeamColor.BLACK)){
                                currentGame.getChessGame().setGameHasEnded(true);
                                this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                        ServerMessage.ServerMessageType.NOTIFICATION,
                                        sessionAuth.getUsername() + " the black player is in checkmate"));

                            }else if(currentGame.getChessGame().isInCheck(ChessGame.TeamColor.BLACK)){

                                this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                        ServerMessage.ServerMessageType.NOTIFICATION,
                                        sessionAuth.getUsername() + " the black player is in check"));

                            }else if(currentGame.getChessGame().isInStalemate(ChessGame.TeamColor.BLACK)){
                                currentGame.getChessGame().setGameHasEnded(true);
                                this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                        ServerMessage.ServerMessageType.NOTIFICATION,
                                        sessionAuth.getUsername() + " the game ends in stalemate"));

                            }
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                        }catch(InvalidMoveException ex){
                            throw new DataAccessException(ex.getMessage());
                        }
                    }else if(currentGame.getBlackUsername() != null &&
                            currentGame.getBlackUsername().equals(sessionAuth.getUsername())){
                        // check black player move
                        if(currentGame.getChessGame().getTeamTurn() != ChessGame.TeamColor.BLACK){
                            throw new DataAccessException("It is not your turn");
                        }
                        try{
                            if(currentGame.getChessGame().validMoves(command.getChessMove().getStartPosition()) == null){
                                throw new DataAccessException("that move is invalid");
                            }
                            if(!currentGame.getChessGame().validMoves(command.getChessMove().
                                    getStartPosition()).contains(command.getChessMove())){
                                throw new DataAccessException("that move is invalid");
                            }
                            currentGame.getChessGame().makeMove(command.getChessMove());

                            this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the black player make a move"));

                            this.broadCast(command.getGameID(), null, new LoadGameServerMessage(
                                    ServerMessage.ServerMessageType.LOAD_GAME,currentGame.getChessGame()));

                            if(currentGame.getChessGame().isInCheckmate(ChessGame.TeamColor.WHITE)){
                                currentGame.getChessGame().setGameHasEnded(true);
                                this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                        ServerMessage.ServerMessageType.NOTIFICATION,
                                        sessionAuth.getUsername() + " the white player is in checkmate"));

                            }else if(currentGame.getChessGame().isInCheck(ChessGame.TeamColor.WHITE)){

                                this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                        ServerMessage.ServerMessageType.NOTIFICATION,
                                        sessionAuth.getUsername() + " the white player is in check"));

                            }else if(currentGame.getChessGame().isInStalemate(ChessGame.TeamColor.WHITE)){
                                currentGame.getChessGame().setGameHasEnded(true);
                                this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                        ServerMessage.ServerMessageType.NOTIFICATION,
                                        sessionAuth.getUsername() + " the game ends in stalemate"));

                            }
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                        }catch(InvalidMoveException ex){
                            throw new DataAccessException(ex.getMessage());
                        }
                    }else{
                        throw new DataAccessException("You are not a player in this game");
                    }
                }else{
                    throw new DataAccessException("Game does not exist");
                }
            }else{
                throw new DataAccessException("unauthorized");
            }
        }catch( Exception ex){
            this.sendMessage(session, new ErrorServerMessage(ServerMessage.ServerMessageType.ERROR, ex.getMessage()));
        }
    }

    private void leaveSocketHandler(Session session, UserGameCommand command) throws IOException {
        try{
            if(sqlAuthObj.getAuth(command.getAuthToken()) != null){
                AuthData sessionAuth = sqlAuthObj.getAuth(command.getAuthToken());
                if(sqlGameObj.getGame(command.getGameID()) != null){
                    if(!connections.getGameSessions(command.getGameID()).contains(session)){
                        throw new DataAccessException("You are not connected to that game");
                    }
                    connections.remove(command.getGameID(), session);
                    GameData currentGame = sqlGameObj.getGame(command.getGameID());
                    if(sqlGameObj.getGame(command.getGameID()).getWhiteUsername() != null &&
                            sqlGameObj.getGame(command.getGameID()).getBlackUsername() != null){
                        if(sqlGameObj.getGame(command.getGameID()).getWhiteUsername().
                                equals(sessionAuth.getUsername())){
                            currentGame.setWhiteUsername(null);
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                            this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the white player left the game"));
                        }else if(sqlGameObj.getGame(command.getGameID()).getBlackUsername().
                                equals(sessionAuth.getUsername())){
                            currentGame.setBlackUsername(null);
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                            this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the black player left the game"));
                        }else{
                            this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,"Observer " +
                                    sessionAuth.getUsername() + " left the game"));
                        }
                    }else if(sqlGameObj.getGame(command.getGameID()).getWhiteUsername() == null &&
                            sqlGameObj.getGame(command.getGameID()).getBlackUsername() != null){
                        if(sqlGameObj.getGame(command.getGameID()).getBlackUsername().
                                equals(sessionAuth.getUsername())){
                            currentGame.setBlackUsername(null);
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                            this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the black player left the game"));
                        }else{
                            this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,"Observer " +
                                    sessionAuth.getUsername() + " left the game"));
                        }
                    } else if(sqlGameObj.getGame(command.getGameID()).getWhiteUsername() != null &&
                            sqlGameObj.getGame(command.getGameID()).getBlackUsername() == null){
                        if(sqlGameObj.getGame(command.getGameID()).getWhiteUsername().
                                equals(sessionAuth.getUsername())){
                            currentGame.setWhiteUsername(null);
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                            this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the white player left the game"));
                        }else{
                            this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,"Observer " +
                                    sessionAuth.getUsername() + " left the game"));
                        }
                    }else{
                        this.broadCast(command.getGameID(), session,new NotificationServerMessage(
                                ServerMessage.ServerMessageType.NOTIFICATION,"Observer " +
                                sessionAuth.getUsername() + " left the game"));
                    }
                }else{
                    throw new DataAccessException("unauthorized");
                }
            }else{
                throw new DataAccessException("unauthorized");
            }
        }catch( Exception ex){
            this.sendMessage(session, new ErrorServerMessage(ServerMessage.ServerMessageType.ERROR, ex.getMessage()));
        }
    }

    private void resignSocketHandler(Session session, UserGameCommand command) throws IOException {
        try{
            if(sqlAuthObj.getAuth(command.getAuthToken()) != null){
                AuthData sessionAuth = sqlAuthObj.getAuth(command.getAuthToken());
                if(sqlGameObj.getGame(command.getGameID()) != null){
                    if(!connections.getGameSessions(command.getGameID()).contains(session)){
                        throw new DataAccessException("You are not connected to that game");
                    }
                    GameData currentGame = sqlGameObj.getGame(command.getGameID());
                    if(currentGame.getChessGame().isGameHasEnded()){
                        throw new DataAccessException("The game has ended, you can not resign");
                    }
                    if(currentGame.getWhiteUsername() != null &&
                    currentGame.getBlackUsername() != null){
                        if(currentGame.getWhiteUsername().equals(sessionAuth.getUsername())){
                            currentGame.getChessGame().setGameHasEnded(true);
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                            this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the white player resigned"));

                        }else if(currentGame.getBlackUsername().equals(sessionAuth.getUsername())){
                            currentGame.getChessGame().setGameHasEnded(true);
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                            this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the black player resigned"));
                        }else{
                            throw new DataAccessException("You are not a player in this game");
                        }
                    }else if(currentGame.getWhiteUsername() == null &&
                            currentGame.getBlackUsername() != null){
                        if(currentGame.getBlackUsername().equals(sessionAuth.getUsername())){
                            currentGame.getChessGame().setGameHasEnded(true);
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                            this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the black player resigned"));
                        }else{
                            throw new DataAccessException("You are not a player in this game");
                        }
                    }else if(currentGame.getWhiteUsername() != null &&
                            currentGame.getBlackUsername() == null){
                        if(currentGame.getWhiteUsername().equals(sessionAuth.getUsername())){
                            currentGame.getChessGame().setGameHasEnded(true);
                            sqlGameObj.updateGame(command.getGameID(), currentGame);
                            this.broadCast(command.getGameID(), null, new NotificationServerMessage(
                                    ServerMessage.ServerMessageType.NOTIFICATION,
                                    sessionAuth.getUsername() + " the white player resigned"));

                        }else{
                            throw new DataAccessException("You are not a player in this game");
                        }
                    }else{
                        throw new DataAccessException("You are not a player in this game");
                    }
                }else{
                    throw new DataAccessException("unauthorized");
                }
            }else{
                throw new DataAccessException("unauthorized");
            }
        }catch( Exception ex){
            this.sendMessage(session, new ErrorServerMessage(ServerMessage.ServerMessageType.ERROR, ex.getMessage()));
        }
    }

    public void sendMessage(Session session, ServerMessage message) throws IOException {
        Gson serializer = new GsonBuilder().enableComplexMapKeySerialization().create();
        session.getRemote().sendString(serializer.toJson(message));
    }
    public void broadCast(Integer gameId, Session excludedSession, ServerMessage broadCastMessage) throws IOException {
        Set<Session> currentGameSessionSet = connections.getGameSessions(gameId);
        if(currentGameSessionSet != null){
            for( Session currentSession: currentGameSessionSet){
                if(!currentSession.equals(excludedSession)){
                    if(currentSession.isOpen()){
                        this.sendMessage(currentSession, broadCastMessage);
                    }
                }
            }
        }

    }
}