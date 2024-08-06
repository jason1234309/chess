package server;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    ConcurrentHashMap<Integer, Set<Session>> gameSessionsMap = new ConcurrentHashMap<>();

    public void add(Integer gameID, Session session) {
        if(gameSessionsMap.containsKey(gameID)){
            gameSessionsMap.get(gameID).add(session);
        }else{
            Set<Session> newSessionSet = new HashSet<>();
            newSessionSet.add(session);
            gameSessionsMap.put(gameID, newSessionSet);
        }
    }

    public void remove(Integer gameID,Session session) {
        if(gameSessionsMap.containsKey(gameID)){
            Set<Session> desiredSet = gameSessionsMap.get(gameID); // can remove after error check
            if(desiredSet.contains(session)){  // think this is needed for error checking
                gameSessionsMap.get(gameID).remove(session);
            } // should add else that prints an error message
        }
    }
    public void broadCast(Integer gameId, Session excludedSession, ServerMessage broadCastMessage){

    }



}
