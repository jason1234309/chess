package server;

import dataaccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import java.util.HashSet;
import java.util.Map;
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
    public void removeFromAllGames(Session session) throws DataAccessException {
        for(Map.Entry<Integer, Set<Session>> currentEntry: gameSessionsMap.entrySet()){
            for(Session currentSession: currentEntry.getValue()){
                if(currentSession.equals(session)){
                    gameSessionsMap.get(currentEntry.getKey()).remove(session);
                }
            }
        }
    }

    public void remove(Integer gameID,Session session) throws DataAccessException {
        if(gameSessionsMap.containsKey(gameID)){
            Set<Session> desiredSet = gameSessionsMap.get(gameID); // can remove after error check
            if(desiredSet.contains(session)){  // think this is needed for error checking
                gameSessionsMap.get(gameID).remove(session);
            }else{
                throw new DataAccessException("unauthorized");
            }
        }else{
            throw new DataAccessException("unauthorized");
        }
    }
    public Set<Session> getGameSessions(Integer gameID){
        return gameSessionsMap.getOrDefault(gameID, null);
    }
}
